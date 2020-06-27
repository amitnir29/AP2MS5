using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace FlightMobileWeb.Command
{
    /// <summary>
    /// This class is the command object sent from the client to the server
    /// when one of the user's controllers changes. 
    /// This object's fields are later being sent to the simulator if they have changed
    /// from the previous command object sent.
    /// </summary>
    public class CommandObject
    {
        /// <summary>
        /// These are the client's controllers fields.
        /// </summary>
        private double aileron;
        private double rudder;
        private double elevator;
        private double throttle;

        /// <summary>
        /// The constructor.
        /// Used to convert the json from the client to an object
        /// that will later be sent to the simulator
        /// </summary>
        /// <param name="clientAileron"></param>
        /// <param name="clientRudder"></param>
        /// <param name="clientElevator"></param>
        /// <param name="clientThrottle"></param>
        public CommandObject(double clientAileron, double clientRudder,
            double clientElevator, double clientThrottle)
        {
            Aileron = clientAileron;
            Rudder = clientRudder;
            Elevator = clientElevator;
            Throttle = clientThrottle;
        }

        /// <summary>
        /// Default constructor that the http demands.
        /// </summary>
        public CommandObject()
        {
        }

        /// <summary>
        /// The client's plane's aileron.
        /// </summary>
        [JsonPropertyName("aileron")]
        public double Aileron
        {
            get => this.aileron;
            set => this.aileron = value;
        }

        /// <summary>
        /// The client's plane's rudder.
        /// </summary>
        [JsonPropertyName("rudder")]
        public double Rudder
        {
            get => this.rudder;
            set => this.rudder = value;
        }

        /// <summary>
        /// The client's plane's elevator.
        /// </summary>
        [JsonPropertyName("elevator")]
        public double Elevator
        {
            get => this.elevator;
            set => this.elevator = value;
        }

        /// <summary>
        /// The client's plane's throttle.
        /// </summary>
        [JsonPropertyName("throttle")]
        public double Throttle
        {
            get => this.throttle;
            set => this.throttle = value;
        }


    }
}
