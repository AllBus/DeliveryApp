package com.tytosoft.delivery.views.dots;

enum EDotState {
		/**
		 * A Dot in this State currently reflects the inactive parameters, and is not
		 * transitioning.
		 */
		INACTIVE(true, null, null),

		/**
		 * A Dot in this State currently reflects the active parameters, and is not transitioning.
		 */
		ACTIVE(true, null, null),

		/**
		 * A Dot in this State does not currently reflect either the active or inactive parameters,
		 * and is transitioning towards the active state.
		 */
		TRANSITIONING_TO_ACTIVE(false, ACTIVE, INACTIVE),

		/**
		 * A Dot in this State does not currently reflect either the active or inactive parameters,
		 * and is transitioning towards the inactive state.
		 */
		TRANSITIONING_TO_INACTIVE(false, INACTIVE, ACTIVE);

		/**
		 * Indicates whether or not a Dot in this State has constant size and color.
		 */
		private final boolean isStable;

		/**
		 * The State this State is transitioning towards, null if this State is stable.
		 */
		private final EDotState to;

		/**
		 * The State this State is transitioning from, null if this State is stable.
		 */
		private final EDotState from;

		/**
		 * Constructs a new State instance.
		 *
		 * @param isStable
		 * 		whether or not a Dot in this State has constant size and color
		 * @param to
		 * 		the State this State is transitioning to, null if this State is stable
		 * @param from
		 * 		the State this State is transitioning from, null if this State is stable
		 */
		EDotState(final boolean isStable, final EDotState to, final EDotState from) {
			this.isStable = isStable;
			this.to = to;
			this.from = from;
		}

		/**
		 * @return whether or not a Dot in this State has constant size and color
		 */
		public boolean isStable() {
			return isStable;
		}

		/**
		 * @return the State this State is transitioning towards, null if this State is stable
		 */
		public EDotState transitioningTo() {
			return to;
		}

		/**
		 * @return the State this State is transitioning from, null if this State is stable
		 */
		public EDotState transitioningFrom() {
			return from;
		}
	}