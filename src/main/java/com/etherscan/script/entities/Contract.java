package com.etherscan.script.entities;

import com.etherscan.script.utils.DateUtils;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

import static com.etherscan.script.utils.DateUtils.DATE_TIME_FORMAT_GENERAL;

@Entity
@Data
public class Contract
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String contract;
    private String url;
    private Integer lineNumber;
    @UpdateTimestamp
    private Date lastUpdated;
    private boolean enabled;

    @Data
    public static class Dto
    {
        private Integer id;
        private String contract;
        private String url;
        private String lastUpdated;
        private Integer row;
        private boolean enabled;

        public static Dto fromEntity(Contract contract)
        {
            Dto dto = new Dto();
            dto.setId(contract.getId());
            dto.setContract(contract.getContract());
            dto.setEnabled(contract.isEnabled());
            dto.setRow(contract.getLineNumber());
            dto.setUrl(contract.getUrl());
            dto.setLastUpdated(contract.getLastUpdated() != null
                ? DateUtils.format(contract.getLastUpdated(), DATE_TIME_FORMAT_GENERAL)
                : "");
            return dto;
        }

        public Contract createEntity()
        {
            Contract contract = new Contract();
            contract.setContract(getContract());
            contract.setEnabled(isEnabled());
            contract.setLineNumber(getRow());
            return contract;
        }
    }
}
