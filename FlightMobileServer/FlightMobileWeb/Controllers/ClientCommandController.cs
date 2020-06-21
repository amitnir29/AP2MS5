using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using FlightMobileWeb.Command;
using FlightMobileWeb.TCPConnection;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace FlightMobileWeb.Controllers
{
    [Route("api/command")]
    [ApiController]
    public class ClientCommandController : ControllerBase
    {
        private ISimulatorFieldsCommander simulatorFieldsCommander;

        public ClientCommandController(ISimulatorFieldsCommander sfc)
        {
            this.simulatorFieldsCommander = sfc;
        }

        /// <summary>
        /// A controller for the post method.
        /// Send the server a new Command object that contains the new client fields.
        /// </summary>
        /// <param name="command"> An object that contains the new fields. </param>
        /// <returns> The result of the post action. </returns>
        [HttpPost]
        public async Task<IActionResult> Post([FromBody] CommandObject command)
        {
            try
            {
                HttpResult res = await this.simulatorFieldsCommander.HandleNewCommand(command);
                if (res.CommandResult == Result.Ok)
                {
                    return Ok(res.Message);
                } else
                {
                    return BadRequest(res.Message);
                }
            }
            catch (ArgumentException e)
            {
                return BadRequest(e.Message);
            }
            catch (Exception e)
            {
                return BadRequest(e.Message);
            }
        }


    }
}