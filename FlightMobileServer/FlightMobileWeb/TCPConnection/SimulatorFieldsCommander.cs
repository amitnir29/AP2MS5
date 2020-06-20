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
    public enum Result { Ok,NotOk};
    public class SimulatorFieldsCommander : ISimulatorFieldsCommander
    {
        private readonly BlockingCollection<AsyncCommand> _queue;
        private readonly TcpClient _client;
        //private readonly string[]
        private string[] blah;
        //private 
        private double prevAileron;
        private double prevRudder;
        private double prevElevator;
        private double prevThrottle;
        private double PrevAileron
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
        private readonly string flightSimHost;
        private readonly int flightSimPort;
        public SimulatorFieldsCommander(IConfiguration conf)
        {
            _queue = new BlockingCollection<AsyncCommand>();
            _client = new TcpClient();
            this.flightSimHost = conf["SimulatorHost"];
            this.flightSimPort = Int32.Parse(conf["SimulatorPort"]);
        }
        public Task<Result> HandleNewCommand(CommandObject newCommand)
        {
            /* string s = "";
             s += newCommand.Aileron.ToString();
             s += newCommand.Rudder.ToString();
             s += newCommand.Throttle.ToString();
             s += newCommand.Elevator.ToString();*/
            AsyncCommand asyncCommand = new AsyncCommand(newCommand);
            _queue.Add(asyncCommand);
            //return Task.Run(()=>s);
            return asyncCommand.Task;
        }
        public void Start()
        {
            Task.Factory.StartNew(HandleCommands);
        }

        public void HandleCommands()
        {
            _client.Connect(this.flightSimHost,this.flightSimPort);
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
            bool isOk = true;
            string[] fieldNames = { "Aileron", "Rudder", "Elevator", "Throttle" };
            IDictionary<string, string> pathFields = new Dictionary<string, string>();
            pathFields.Add("Aileron", "/controls/flight/aileron");
            pathFields.Add("Rudder", "/controls/flight/rudder");
            pathFields.Add("Elevator", "s/controls/flight/elevator");
            pathFields.Add("Throttle", "/controls/engines/current-engine/throttle");
            // Send the message to the connected TcpServer.
            CommandObject command = asyncCommand.Command;
            foreach(string field in fieldNames)
            {
                double currentValue = (double)asyncCommand.GetType().GetProperty(field).GetValue(asyncCommand);
                double prevValue = (double)this.GetType().GetProperty("Prev"+field).GetValue(this);
                if (prevValue == currentValue)
                {
                    continue;
                }
                string message = "set "+pathFields[field]+" "+ currentValue.ToString()+" \n";
                Byte[] data = System.Text.Encoding.ASCII.GetBytes(message);
                stream.Write(data, 0, data.Length);
                //check if it's the same field
                // Buffer to store the response bytes.
                data = new Byte[256];

                // String to store the response ASCII representation.
                String responseData = String.Empty;

                // Read the first batch of the TcpServer response bytes.
                Int32 bytes = stream.Read(data, 0, data.Length);
                responseData = System.Text.Encoding.ASCII.GetString(data, 0, bytes);
                double returnedValue;
                //check if it's a double
                if (!Double.TryParse(responseData,out returnedValue))
                {
                    //TODO- return error
                    isOk = false;
                }
                //if not the same value returned from server, call an error
                if (returnedValue != currentValue)
                {
                    isOk = false;
                }
            }
            //check the bool to see what's the returned val
            if (isOk)
            {
                asyncCommand.Completion.SetResult(Result.Ok);
            }
            else
            {
                asyncCommand.Completion.SetResult(Result.NotOk);
            }

        }
    }
}
