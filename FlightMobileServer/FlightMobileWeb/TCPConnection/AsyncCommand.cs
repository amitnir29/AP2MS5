using FlightMobileWeb.Command;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FlightMobileWeb.TCPConnection
{
    public class AsyncCommand
    {
        public CommandObject Command { get; private set; }
        public TaskCompletionSource<HttpResult> Completion { get; private set;}
        public Task<HttpResult> Task { get => Completion.Task; }
        public AsyncCommand(CommandObject c)
        {
            Command = c;
            Completion = new TaskCompletionSource<HttpResult>
                (TaskCreationOptions.RunContinuationsAsynchronously);
        }
    }
}
