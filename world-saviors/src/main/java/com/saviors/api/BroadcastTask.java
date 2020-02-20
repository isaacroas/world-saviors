package com.saviors.api;

import java.util.TimerTask;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent.Builder;
import javax.ws.rs.sse.SseBroadcaster;

import com.saviors.domain.SseMessage;

public class BroadcastTask extends TimerTask {
	
	private Builder eventBuilder;
	private SseBroadcaster sseBroadcaster;
	private int iterations;

	public BroadcastTask(Builder eventBuilder, SseBroadcaster sseBroadcaster) {
		this.eventBuilder = eventBuilder;
		this.sseBroadcaster = sseBroadcaster;
		this.iterations = 0;
	}

	@Override
	public void run() {
		this.iterations++;
		System.out.println("broad");
		sseBroadcaster.broadcast(
				eventBuilder.data(new SseMessage("This is savior # " + iterations))
				.mediaType(MediaType.APPLICATION_JSON_TYPE).build());
	}

}
