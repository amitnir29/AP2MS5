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
        public Task<IActionResult> HandleNewCommand(CommandObject newCommand)
        {
            return null;
        }
    }
}
