using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using FlightMobileWeb.Screenshot;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace FlightMobileWeb.Controllers
{
    [Route("screenshot")]
    [ApiController]
    public class ScreenshotController : ControllerBase
    {
        private ISimulatorScreenshotGetter simulatorScreenshotGetter;

        public ScreenshotController(ISimulatorScreenshotGetter ssg)
        {
            this.simulatorScreenshotGetter = ssg;
        }


        /*

        /// <summary>
        /// Get the screenshot of the current state of the simulator.
        /// </summary>
        /// <returns> a jpg image of the current state </returns>
        [HttpGet]
        public async Task<ActionResult> Get()
        {
            //return await this.simulatorScreenshotGetter.GetScreenshotFromSimulator();
        }
        */


    }
}