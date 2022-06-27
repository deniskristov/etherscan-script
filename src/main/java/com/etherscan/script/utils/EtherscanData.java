package com.etherscan.script.utils;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EtherscanData
{
    private String contract;
    private Integer row;
    private String url;
    private LocalDateTime lastUpdated;
}
