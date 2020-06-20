using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;

namespace FlightMobileWeb.Screenshot
{

    public class SimulatorScreenshotGetter : ISimulatorScreenshotGetter
    {
        private HttpClient simulatorClient;
        private string simulatorScreenshotPath;

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
            //TODO replace from app config
            return "http://" + configuration["SimulatorHost"] + ':' + configuration["SimulatorPort"] + "/screenshot";
        }

        public async Task<byte[]> GetScreenshotFromSimulator()
        {
            try
            {

                byte[] ba = await this.simulatorClient.GetByteArrayAsync(this.simulatorScreenshotPath);
                return ba;
            }
            catch
            {
                //TODO error
                return null;
            }
        }
    }

}
