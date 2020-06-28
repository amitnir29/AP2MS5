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
    /// <summary>
    /// A controller for the screenshots from the simulator.
    /// </summary>
    [Route("screenshot")]
    [ApiController]
    public class ScreenshotController : ControllerBase
    {
        private ISimulatorScreenshotGetter simulatorScreenshotGetter;
        private IConfiguration configuration;

        /// <summary>
        /// The constructor.
        /// </summary>
        /// <param name="ssg"> An http client that gets screenshots from thee simulator. </param>
        /// <param name="configuration"> the object to get configuration vars from. </param>
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
        public async Task<IActionResult> Get()
        {
            byte[] pic = await this.simulatorScreenshotGetter.GetScreenshotFromSimulator();
            //check if got a picture
            if (pic == null)
            {
                return BadRequest("got null picture");
            }
            //check if the data can be converted to a picture.
            try
            {
                return File(pic, "image/jpeg");
            }
            catch
            {
                return BadRequest("could not parse the simulator answer to a jpg file");
            }
            
        }

    }
}