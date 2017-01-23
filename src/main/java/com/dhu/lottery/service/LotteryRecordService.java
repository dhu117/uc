package com.dhu.lottery.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhu.common.util.SpringContextUtil;
import com.dhu.common.util.StringUtil;
import com.dhu.lottery.dao.LotteryRecordDao;
import com.dhu.lottery.model.LotteryRecord;
import com.dhu.lottery.model.LotteryRule;
import com.dhu.lottery.rule.ILotteryRule;

@Service
public class LotteryRecordService {
	@Autowired
	LotteryRecordDao lotteryRecordDao;

	public String getLotteryMiss() {
		List<LotteryRecord> records = lotteryRecordDao.getTodayLotteryRecord();
		List<LotteryRule> rules = lotteryRecordDao.getAllRule();
		List<ILotteryRule> ruleList = new ArrayList<>();
		if (rules != null) {
			for (LotteryRule lr : rules) {
				ruleList.add((ILotteryRule) SpringContextUtil.getBean(lr.getRuleCode()));
			}
		}
		StringBuilder result = new StringBuilder();
		for (ILotteryRule ilr : ruleList) {
			if (ilr.isMatch(records)) {
				result.append(ilr.getRuleResult()).append(";");
			}
		}
		if (result.length() > 0) {
			return result.toString();
		}
		return StringUtil.EMPTY;
	}

}
