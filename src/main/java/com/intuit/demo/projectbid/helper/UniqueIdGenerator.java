package com.intuit.demo.projectbid.helper;

import java.util.concurrent.atomic.AtomicLong;

public enum UniqueIdGenerator {
	INSTANCE;
	private AtomicLong projectSeq = new AtomicLong(1000);
	private AtomicLong bidSeq = new AtomicLong(100000);
	
	public long incrementProjectSequence() {
		return projectSeq.incrementAndGet();
	}
	public long incrementBidSequence() {
		return bidSeq.incrementAndGet();
	}
}
