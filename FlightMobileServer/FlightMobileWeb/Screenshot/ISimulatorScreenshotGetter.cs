using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

namespace FlightMobileWeb.Screenshot
{
    public interface ISimulatorScreenshotGetter
    {
        /// <summary>
        /// Get the current simulator's screenshot.
        /// </summary>
        /// <returns> a jpg file of the simulator's screenshot. </returns>
        public Task<byte[]> GetScreenshotFromSimulator();
    }
}
