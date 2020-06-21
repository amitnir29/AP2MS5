using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FlightMobileWeb.TCPConnection
{
    public class HttpResult
    {
        public Result CommandResult
        {
            get; set;
        }

        public string Message
        {
            get; set;
        }

        public HttpResult(Result result, string message)
        {
            this.CommandResult = result;
            this.Message = message;
        }

        public HttpResult()
        {
            this.CommandResult = Result.Ok;
            this.Message = "";
        }

        public void AddToMessage(string toAdd)
        {
            this.Message += toAdd;
        }

        public void AddPostError(string fieldName, double wanted, string returned)
        {
            this.AddToMessage("Error in field " + fieldName + ", sent " +
                wanted + " and got " + returned + "\n");
        }
    }
}
