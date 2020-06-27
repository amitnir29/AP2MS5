using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;

namespace FlightMobileWeb.Screenshot
{
    /// <summary>
    /// This class uses http to get screenshots from the simulator.
    /// </summary>
    public class SimulatorScreenshotGetter : ISimulatorScreenshotGetter
    {
        /// <summary>
        /// A client to use to make connection with the simulator.
        /// </summary>
        private HttpClient simulatorClient;
        /// <summary>
        /// The simulator's path to send requests to.
        /// </summary>
        private string simulatorScreenshotPath;

        /// <summary>
        /// The constructor.
        /// </summary>
        /// <param name="configuration"> the object to get configuration vars from. </param>
        public SimulatorScreenshotGetter(IConfiguration configuration)
        {
            this.simulatorClient = new HttpClient();
            this.simulatorScreenshotPath = this.GetSimulatorScreenshotPath(configuration);
        }

        /// <summary>
        /// Gets the simulator's details from the project configuration and created the
        /// string of the simulator's path for the screenshot get.
        /// </summary>
        /// <returns> The simulator's screenshot path. </returns>
        private string GetSimulatorScreenshotPath(IConfiguration configuration)
        {
            return "http://" + configuration["SimulatorHost"] +
                ':' + configuration["SimulatorHttpPort"] + "/screenshot";
        }

        public async Task<byte[]> GetScreenshotFromSimulator()
        {
            try
            {
                byte[] ba = await 
                    this.simulatorClient.GetByteArrayAsync(this.simulatorScreenshotPath);
                return ba;
            }
            catch
            {
                return null;
            }
        }
    }

}
