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
        private readonly BlockingCollection<AsyncCommand> _queue;
        private readonly TcpClient _client;
        //private 
        private double? prevAileron;
        private double? prevRudder;
        private double? prevElevator;
        private double? prevThrottle;
        public double? PrevAileron
        {
            get => prevAileron;
            set => prevAileron = value;
        }
        public double? PrevRudder
        {
            get => this.prevRudder;
            set => this.prevRudder = value;
        }
        public double? PrevElevator
        {
            get => this.prevElevator;
            set => this.prevElevator = value;
        }
        public double? PrevThrottle
        {
            get => this.prevThrottle;
            set => this.prevThrottle = value;
        }
        //the simulator's fields' paths
        private static readonly Dictionary<string, string> pathFields = new Dictionary<string, string>
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
            this.flightSimPort = Int32.Parse(conf["SimulatorPort"]);
            //
            this.PrevAileron = Double.PositiveInfinity;
            this.PrevElevator = Double.PositiveInfinity;
            this.PrevRudder = Double.PositiveInfinity;
            this.PrevThrottle = Double.PositiveInfinity;
            //now start the tcp connection
            this.Start();
        }
        public Task<HttpResult> HandleNewCommand(CommandObject newCommand)
        {
            AsyncCommand asyncCommand = new AsyncCommand(newCommand);
            _queue.Add(asyncCommand);
            return asyncCommand.Task;
        }
        public void Start()
        {
            Task.Factory.StartNew(HandleCommands);
        }

        public void HandleCommands()
        {
            _client.Connect(this.flightSimHost, this.flightSimPort);
            NetworkStream stream = _client.GetStream();
            //send the initial data\n
            string initialConnection = "data\n";
            Byte[] initialData = System.Text.Encoding.ASCII.GetBytes(initialConnection);
            stream.Write(initialData, 0, initialData.Length);
            foreach (AsyncCommand asyncCommand in _queue.GetConsumingEnumerable())
            {
                SendCommand(asyncCommand, stream);
            }

        }
        public void Stop()
        {
            _client.GetStream().Close();
            _client.Close();
        }
        private void SendCommand(AsyncCommand asyncCommand, NetworkStream stream)
        {
            HttpResult result = new HttpResult();
            // Send the message to the connected TcpServer.
            CommandObject command = asyncCommand.Command;
            foreach (KeyValuePair<string, string> field in pathFields)
            {
                double currentValue = (double)command.GetType()
                    .GetProperty(field.Key).GetValue(command);
                double? prevValue = Double.PositiveInfinity;
                if (this.GetType().GetProperty("Prev" + field.Key) != null)
                {
                    prevValue = (double?)this.GetType().GetProperty("Prev" + field.Key)
                        .GetValue(this);
                }
                if (prevValue == currentValue)
                {
                    continue;
                }
                string message = "set " + field.Value + " " + currentValue.ToString() + " \n";
                Byte[] data = System.Text.Encoding.ASCII.GetBytes(message);
                stream.Write(data, 0, data.Length);
                //check if it's the same field
                message = "get " + field.Value + " \n";
                data = System.Text.Encoding.ASCII.GetBytes(message);
                stream.Write(data, 0, data.Length);
                // Buffer to store the response bytes.
                data = new Byte[256];

                // String to store the response ASCII representation.
                String responseData = String.Empty;

                // Read the first batch of the TcpServer response bytes.
                Int32 bytes = stream.Read(data, 0, data.Length);
                responseData = System.Text.Encoding.ASCII.GetString(data, 0, bytes);
                double returnedValue;
                //if it's not a double, or a double with wrong value, set error result
                if (!Double.TryParse(responseData, out returnedValue) ||
                    returnedValue != currentValue)
                {
                    result.CommandResult = Result.NotOk;
                    result.AddPostError(field.Key, currentValue, responseData);
                }
                this.GetType().GetProperty("Prev" + field.Key).SetValue(this, returnedValue);
            }
            //set the return status
            asyncCommand.Completion.SetResult(result);

        }
    }
}
