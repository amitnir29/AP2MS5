using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FlightMobileWeb.TCPConnection
{   
    /// <summary>
    /// This is a data class for the http requests.
    /// </summary>
    public class HttpResult
    {
        /// <summary>
        /// The result of the request: OK or not.
        /// </summary>
        public Result CommandResult
        {
            get; set;
        }

        /// <summary>
        /// An additional message about the result.
        /// </summary>
        public string Message
        {
            get; set;
        }

        /// <summary>
        /// A default constructor with empty message and assumed OK result.
        /// </summary>
        public HttpResult()
        {
            this.CommandResult = Result.Ok;
            this.Message = "";
        }

        /// <summary>
        /// Add the input string to the result message.
        /// </summary>
        /// <param name="toAdd"> the string to add. </param>
        public void AddToMessage(string toAdd)
        {
            this.Message += toAdd;
        }

        /// <summary>
        /// Add an error message about a bad command post request.
        /// </summary>
        /// <param name="fieldName"> the field that was posted. </param>
        /// <param name="wanted"> the value that was posted. </param>
        /// <param name="returned"> the bad value that has returned. </param>
        public void AddCommandPostError(string fieldName, double wanted, string returned)
        {
            this.AddToMessage("Error in field " + fieldName + ", sent " +
                wanted + " and got " + returned + "\n");
        }
    }
}
