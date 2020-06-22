using FlightMobileWeb.Command;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FlightMobileWeb.TCPConnection
{
    public class AsyncCommand
    {
        /// <summary>
        /// represents the values of the json file sent
        /// </summary>
        public CommandObject Command { get; private set; }
        /// <summary>
        /// used to synchronize between the controller and the tcp client
        /// </summary>
        public TaskCompletionSource<HttpResult> Completion { get; private set;}
        /// <summary>
        /// the task that will be awaited on
        /// </summary>
        public Task<HttpResult> Task { get => Completion.Task; }
        public AsyncCommand(CommandObject c)
        {
            Command = c;
            Completion = new TaskCompletionSource<HttpResult>
                (TaskCreationOptions.RunContinuationsAsynchronously);
        }
    }
}
