package Service;

import Entities.Contract;
import Entities.Installment;

import java.util.Calendar;
import java.util.Date;

public class ContractService {

    private OnlinePaymentService onlinePaymentService;

    private Date addMonths(Date date, int n){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH,n);
        return cal.getTime();
    }

    public ContractService(OnlinePaymentService onlinePaymentService) {
        this.onlinePaymentService = onlinePaymentService;
    }

    public void processContract(Contract contract, Integer months){
        double basicQuota = contract.getTotalValue() / months;
        for(int i = 1 ; i <= months; i++ ){
            Date date = addMonths(contract.getDate(),i);
            double updatedQuota = basicQuota + onlinePaymentService.interest(basicQuota,i);
            double fullQuota = updatedQuota + onlinePaymentService.paymentFee(updatedQuota);
            contract.addInstallment(new Installment(date,fullQuota));
        }
    }
}