package sorokin.dev.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {

    private final int defaultAmount;
    private final float transferCommission;

    public AccountProperties(
            @Value("${account.default-amount}") int defaultAmount,
            @Value("${account.transfer-commission}") float transferCommission){

        if (defaultAmount < 0)
            throw new IllegalArgumentException("Error: 'default amount' value must be >= 0");

        if ((transferCommission < 0) || (transferCommission > 1))
            throw new IllegalArgumentException("Error: 'transfer commission' value must be between [0, 1]");

        this.defaultAmount = defaultAmount;
        this.transferCommission = transferCommission;
    }

    public int getDefaultAmount() {
        return defaultAmount;
    }

    public float getTransferCommission() {
        return transferCommission;
    }
}
