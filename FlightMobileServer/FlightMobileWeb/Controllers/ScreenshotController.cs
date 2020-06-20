using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using FlightMobileWeb.Screenshot;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;

namespace FlightMobileWeb.Controllers
{
    [Route("screenshot")]
    [ApiController]
    public class ScreenshotController : ControllerBase
    {
        private ISimulatorScreenshotGetter simulatorScreenshotGetter;
        private IConfiguration configuration;

        public ScreenshotController(ISimulatorScreenshotGetter ssg, IConfiguration configuration)
        {
            this.simulatorScreenshotGetter = ssg;
            this.configuration = configuration;
        }


        

        /// <summary>
        /// Get the screenshot of the current state of the simulator.
        /// </summary>
        /// <returns> a jpg image of the current state as bytes array </returns>
        [HttpGet]
        public async Task<ActionResult<byte[]>> Get()
        {
            byte[] pic = await this.simulatorScreenshotGetter.GetScreenshotFromSimulator();
            return File(pic, "image/jpeg");
        }
        


    }
}