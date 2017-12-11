package cfda;

/**
 * Created by liutingna on 2017/12/11.
 *
 * @author liutingna
 */
public class DrugDetailInfo {
    private String drugBatchNum;
    private String drugProductName;
    private String companyNum;
    private String companyAddr;

    public String getDrugBatchNum() {
        return drugBatchNum;
    }

    public void setDrugBatchNum(String drugBatchNum) {
        this.drugBatchNum = drugBatchNum;
    }

    public String getDrugProductName() {
        return drugProductName;
    }

    public void setDrugProductName(String drugProductName) {
        this.drugProductName = drugProductName;
    }

    public String getCompanyNum() {
        return companyNum;
    }

    public void setCompanyNum(String companyNum) {
        this.companyNum = companyNum;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    @Override
    public String toString() {
        return "DrugDetailInfo{" +
                "drugBatchNum='" + drugBatchNum + '\'' +
                ", drugProductName='" + drugProductName + '\'' +
                ", companyNum='" + companyNum + '\'' +
                ", companyAddr='" + companyAddr + '\'' +
                '}';
    }
}
