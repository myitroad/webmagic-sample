package cfda;

/**
 * Created by liutingna on 2017/12/11.
 *
 * @author liutingna
 */
public class DrugListInfo {
    private String drugName;
    private String batchNum;
    private String company;
    private String detailUrl;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    @Override
    public String toString() {
        return "InfoListBean{" +
                "drugName='" + drugName + '\'' +
                ", batchNum='" + batchNum + '\'' +
                ", company='" + company + '\'' +
                ", detailUrl='" + detailUrl + '\'' +
                '}';
    }
}
