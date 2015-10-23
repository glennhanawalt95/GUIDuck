package org.guiduck.expression;

import org.guiduck.data.Data;
import org.guiduck.data.GInt;
import org.guiduck.data.Operable;

class Operations {
	static interface Operation {
		Data apply(Data d1, Data d2);
	}
	
	static class Multiply implements Operation {
		@Override
		public Data apply(Data d1, Data d2) {
			checkOperable(d1, d2);
			return new GInt(((int) d1.value()) * ((int) d2.value()));
		}
	}
	
	static class Divide implements Operation {
		@Override
		public Data apply(Data d1, Data d2) {
			checkOperable(d1, d2);
			return new GInt(((int) d1.value()) / ((int) d2.value()));
		}
	}
	
	static class Mod implements Operation {
		@Override
		public Data apply(Data d1, Data d2) {
			checkOperable(d1, d2);
			return new GInt(((int) d1.value()) % ((int) d2.value()));
		}
	}
	
	static class Add implements Operation {
		@Override
		public Data apply(Data d1, Data d2) {
			checkOperable(d1, d2);
			return new GInt(((int) d1.value()) + ((int) d2.value()));
		}
	}
	
	static class Subtract implements Operation {
		@Override
		public Data apply(Data d1, Data d2) {
			checkOperable(d1, d2);
			return new GInt(((int) d1.value()) - ((int) d2.value()));
		}
	}
	
	private static void checkOperable(Data... data) {
		for(Data d : data) {
			if(!(d instanceof Operable)) {
				throw new ParseException(d);
			}
		}
	}
}
