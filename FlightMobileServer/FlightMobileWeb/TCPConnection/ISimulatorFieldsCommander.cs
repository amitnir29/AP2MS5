using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using FlightMobileWeb.Command;
using Microsoft.AspNetCore.Mvc;

namespace FlightMobileWeb.TCPConnection
{
    /// <summary>
    /// This interface takes care of the tcp connection with the server:
    /// Send the simulator set commands according to the client's commands.
    /// </summary>
    public interface ISimulatorFieldsCommander
    {
        /// <summary>
        /// Posts the new command fields to the simulator.
        /// </summary>
        /// <param name="newCommand"> the command object that contains the new fields </param>
        /// <returns> this action's result </returns>
        public Task<string> HandleNewCommand(CommandObject newCommand);
        //TODO note for sherman: this method should be async i think in the implementation
        //also, maybe make this return the http return value?



        //TODO sherman
    }
}
