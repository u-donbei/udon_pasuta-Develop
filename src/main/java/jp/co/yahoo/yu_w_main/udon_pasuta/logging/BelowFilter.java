package jp.co.yahoo.yu_w_main.udon_pasuta.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class BelowFilter extends Filter<ILoggingEvent> {
	Level level;

	@Override
	public FilterReply decide(ILoggingEvent event) {
		if (!isStarted()) {
			return FilterReply.NEUTRAL;
		}

		if (event.getLevel().isGreaterOrEqual(level)) {
			return FilterReply.DENY;
		} else {
			return FilterReply.NEUTRAL;
		}
	}

	public void setLevel(String level) {
		this.level = Level.toLevel(level);
	}

	public void start() {
		if (level != null) {
			super.start();
		}
	}
}
