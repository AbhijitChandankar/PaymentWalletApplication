package com.cg.service;

import com.cg.dto.AddAmountRequest;
import com.cg.dto.TransferAmount;

public interface WalletService {

	String addAmount(AddAmountRequest request);

	String transferAmount(TransferAmount trequest);

}
