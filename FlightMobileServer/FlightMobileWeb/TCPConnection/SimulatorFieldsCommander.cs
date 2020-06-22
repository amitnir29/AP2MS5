using FlightMobileWeb.Command;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Threading.Tasks;

namespace FlightMobileWeb.TCPConnection
{
    public enum Result { Ok, NotOk };
    public class SimulatorFieldsCommander : ISimulatorFieldsCommander
    {
        /// <summary>
        /// a queue with all of the current commands waiting to be executed
        /// </summary>
        private readonly BlockingCollection<AsyncCommand> _queue;
        private readonly TcpClient _client;
        private double prevAileron;
        private double prevRudder;
        private double prevElevator;
        private double prevThrottle;
        public double PrevAileron
        {
            get => prevAileron;
            set => prevAileron = value;
        }
        public double PrevRudder
        {
            get => this.prevRudder;
            set => this.prevRudder = value;
        }
        public double PrevElevator
        {
            get => this.prevElevator;
            set => this.prevElevator = value;
        }
        public double PrevThrottle
        {
            get => this.prevThrottle;
            set => this.prevThrottle = value;
        }
        //the simulator's fields' paths
        private static readonly Dictionary<string, string> pathFields =
            new Dictionary<string, string>
        {
            { "Aileron", "/controls/flight/aileron" },
            { "Rudder", "/controls/flight/rudder" },
            { "Elevator", "/controls/flight/elevator" },
            { "Throttle", "/controls/engines/current-engine/throttle" }
        };
        private readonly string flightSimHost;
        private readonly int flightSimPort;
        public SimulatorFieldsCommander(IConfiguration conf)
        {
            _queue = new BlockingCollection<AsyncCommand>();
            _client = new TcpClient();
            this.flightSimHost = conf["SimulatorHost"];
            this.flightSimPort = Int32.Parse(conf["SimulatorTcpPort"]);
            //
            this.PrevAileron = Double.PositiveInfinity;
            this.PrevElevator = Double.PositiveInfinity;
            this.PrevRudder = Double.PositiveInfinity;
            this.PrevThrottle = Double.PositiveInfinity;
            //now start the tcp connection
            this.Start();
        }
         ~SimulatorFieldsCommander()
        {
            this.Stop();
        }
        /// <summary>
        /// adds a new command to the queue, called by the controller
        /// </summary>
        /// <param name="newCommand"> the command to add to the queue</param>
        /// <returns></returns>
        public Task<HttpResult> HandleNewCommand(CommandObject newCommand)
        {
            AsyncCommand asyncCommand = new AsyncCommand(newCommand);
            _queue.Add(asyncCommand);
            return asyncCommand.Task;
        }
        /// <summary>
        /// initializes the connection to the simulator
        /// </summary>
        public void Start()
        {
            Task.Factory.StartNew(HandleCommands);
        }
        /// <summary>
        /// 
        /// </summary>
        public void HandleCommands()
        {
            _client.Connect(this.flightSimHost, this.flightSimPort);
            NetworkStream stream = _client.GetStream();
            //send the initial data\n
            string initialConnection = "data\r\n";
            byte[] initialData = new byte[1024];
            initialData = System.Text.Encoding.ASCII.GetBytes(initialConnection);
            stream.Write(initialData, 0, initialData.Length);
            foreach (AsyncCommand asyncCommand in _queue.GetConsumingEnumerable())
            {
                SendCommand(asyncCommand, stream);
            }

        }
        /// <summary>
        /// stops the client's communication
        /// </summary>
        public void Stop()
        {
            _client.GetStream().Close();
            _client.Close();
        }
        /// <summary>
        /// sends a command to the flight simulator
        /// </summary>
        /// <param name="asyncCommand"> the command that will be sent</param>
        /// <param name="stream">the stream that sends the command</param>
        private void SendCommand(AsyncCommand asyncCommand, NetworkStream stream)
        {
            HttpResult result = new HttpResult();
            // iterate over each field that exists
            foreach (KeyValuePair<string, string> field in pathFields)
            {
                handleField(asyncCommand, stream, field, result);
            }
            //set the return status
            asyncCommand.Completion.SetResult(result);
        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="asyncCommand"> the command to execute</param>
        /// <param name="stream">the stream to the simulator</param>
        /// <param name="field">the field name and path in the simulator</param>
        /// <param name="result"> the result that will be returned</param>
        private void handleField(AsyncCommand asyncCommand, NetworkStream stream,
            KeyValuePair<string, string> field, HttpResult result){
            CommandObject command = asyncCommand.Command;
            double currentValue = (double)command.GetType()
                   .GetProperty(field.Key).GetValue(command);
            double prevValue = Double.PositiveInfinity;
            prevValue = (double)this.GetType().GetProperty("Prev" + field.Key).GetValue(this);
            if (prevValue == currentValue){
                return;
            }
            string message = "set " + field.Value + " " + currentValue.ToString() + " \r\n";
            byte[] data = new byte[1024];
            data = System.Text.Encoding.ASCII.GetBytes(message);
            stream.Write(data, 0, data.Length);
            //check if it's the same field
            message = "get " + field.Value + " \r\n";
            data = new byte[1024];
            data = System.Text.Encoding.ASCII.GetBytes(message);
            stream.Write(data, 0, data.Length);
            // Buffer to store the response bytes.
            data = new byte[1024];
            String responseData = String.Empty;
            Int32 bytes = stream.Read(data, 0, data.Length);
            responseData = System.Text.Encoding.ASCII.GetString(data, 0, bytes);
            double returnedValue;
            //if it's not a double, or a double with wrong value, set error result
            if (!Double.TryParse(responseData, out returnedValue) ||
                returnedValue != currentValue){
                result.CommandResult = Result.NotOk;
                result.AddCommandPostError(field.Key, currentValue, responseData);
            }
            this.GetType().GetProperty("Prev" + field.Key).SetValue(this, returnedValue);
        }
    }
}
