package com.dhu.lottery.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dhu.lottery.model.LotteryRecord;
import com.dhu.lottery.model.LotteryRule;

@Repository
public interface LotteryRecordDao {
	List<LotteryRecord> getTodayLotteryRecord();

	void insertLotteryRecord(LotteryRecord record);

	List<LotteryRule> getAllRule();
}