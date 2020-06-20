using FlightMobileWeb.Command;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FlightMobileWeb.TCPConnection
{
    public class SimulatorFieldsCommander : ISimulatorFieldsCommander
    {
        public Task<string> HandleNewCommand(CommandObject newCommand)
        {
            string s = "";
            s += newCommand.Aileron.ToString();
            s += newCommand.Rudder.ToString();
            s += newCommand.Throttle.ToString();
            s += newCommand.Elevator.ToString();
            return Task.Run(()=>s);
        }
    }
}
