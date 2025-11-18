package com.tania.ai.services;

import com.tania.exception.ProductException;
import com.tania.response.ApiResponse;

public interface AiChatBotService {

    ApiResponse aiChatBot(String prompt,Long productId,Long userId) throws ProductException;
}
