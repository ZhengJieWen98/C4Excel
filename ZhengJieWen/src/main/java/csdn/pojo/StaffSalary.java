package csdn.pojo;

/**
 * 员工薪水类
 * 包含员工的详情工资情况
 * 提供相关方法计算工资明细
 */
public class StaffSalary {
    private String department;//部门
    private int id;//员工编号
    private String name;//员工姓名
    private double diXing;//底薪
    private double gangWeiGonGzi;//岗位工资
    private double jiXiaoJiangJin;//绩效奖金
    private double quanQinJiangJin;//全勤奖金
    private double kaoQinKoChu;//考勤扣除
    private double weiGuiChuFa;//违规处罚
    private double jiaoTongBuZhu;//交通补助
    private double tongXinBuZhu;//通信补助

    private double yangLaoBaoXianSelf;//个人养老保险
    private double yiLiaoBaoXianSelf;//个人医疗保险
    private double shiYeBaoXianSelf;//个人失业保险
    private double gongShangBaoXianSelf;//个人工伤保险
    private double shengYuBaoXianSelf;//个人生育保险
    private double gongJiJinSelf;//个人住房公积金

    private double yangLaoBaoXianCompany;//公司养老保险
    private double yiLiaoBaoXianCompany;//公司医疗保险
    private double shiYeBaoXianCompany;//公司失业保险
    private double gongShangBaoXianCompany;//公司工伤保险
    private double shengYuBaoXianCompany;//公司生育保险
    private double gongJiJinCompany;//公司住房公积金


    /**
     * 1.部门薪资明细中的底薪与岗位工资以及绩效、奖金、补助等各项正收益合并至“企业人员月度工资成本支付表.xlsx”的“工资”一栏。
     * @return 正收益之和
     */
    public double getGongzi(){
        return diXing+gangWeiGonGzi+jiXiaoJiangJin+quanQinJiangJin+jiaoTongBuZhu+tongXinBuZhu;
    }

    /**
     * 2.部门薪资明细中的考勤扣除、违规处罚等负收益项全部合并至“企业人员月度工资成本支付表.xlsx”的“扣款”一栏。
     * @return 负收益之和
     */
    public double getKokuan(){
        return kaoQinKoChu+weiGuiChuFa;
    }

    /**
     * 3.“工资-扣款”的计算结果放入“应发工资”一栏。
     * @return
     */
    public double getYingFaGongZi(){
        return getGongzi()-getKokuan();
    }

    /**
     * 4.计算五险一金的具体缴纳数额
     * 包含个人和公司
     * 养老保险：单位，20%，个人，8%
     * 医疗保险：单位，8%，个人，2%；
     * 失业保险：单位，2%，个人，1%；
     * 工伤保险：单位，0.5%，个人不用缴费；
     * 生育保险：单位，0.7%，个人不用缴费；
     * 住房公积金缴纳比例有 8%、10%、12%三档
     * @param jishu 申报基数
     * @param biLie 公积金比率
     */
    public void setWuXianYiJin(double jishu,double biLie){
        //养老保险:单位，20%，个人，8%
        setYangLaoBaoXianCompany(jishu*0.2);
        setYangLaoBaoXianSelf(jishu*0.08);
        //医疗保险:单位，8%，个人，2%；
        setYiLiaoBaoXianCompany(jishu*0.08);
        setYiLiaoBaoXianSelf(jishu*0.02);
        //失业保险:单位，2%，个人，1%；
        setShiYeBaoXianCompany(jishu*0.02);
        setShiYeBaoXianSelf(jishu*0.01);
        //工伤保险：单位，0.5%，个人不用缴费；
        setGongShangBaoXianCompany(jishu*0.005);
        setGongShangBaoXianSelf(jishu*0);
        //生育保险：单位，0.7%，个人不用缴费；
        setShengYuBaoXianCompany(jishu*0.007);
        setShengYuBaoXianSelf(jishu*0);
        //住房公积金缴纳比例有 8%、10%、12%三档  题中没有明确说单位和个人占比
        setGongJiJinCompany(jishu*biLie);
        setGongJiJinSelf(jishu*biLie);
    }

    /**
     * 5.“应发工资+企业缴纳五险一金”的结果填入“企业人员月度工资成本支付表.xlsx“的“企业支出成本”一栏。
     * @return
     */
    public double getCompany(){
        return getYingFaGongZi()+getCompany_WXYJ_All();//住房公积金
    }

    /**
     * 6.“应发工资-五险一金个人缴纳部分”的结果等于“应税金额”，根据应税金额计算个税金额填入“企业人员月度工资成本支付表.xlsx“的“个税金额”一栏
     * 个税计算方式如下：
     * 不考虑个税起征点
     * 收入中不超过3000元的部分按3%税率缴纳个税。
     * 3000元-12000元的部分按10%税率缴纳个税。
     * 超过12000元不高于25000元的部分按税率20%计算。
     * 25000元-35000元的部分按税率25%计算
     * 35000元-55000元的部分按税率30%计算
     * 55000元-80000元的部分按税率35%计算
     * 超过80000元的部分按税率45%计算
     * @return 税的具体金额
     */
    public double getTax(){
        //“应发工资-五险一金个人缴纳部分”的结果等于“应税金额”
        double taxMoney = getYingFaGongZi()-getSelf_WXYJ_All();
        //税
        double tax = 0;
        if(taxMoney<3000){
            tax=taxMoney*0.03;
        }else if(taxMoney<12000){
            tax=taxMoney*0.1;
        }else if(taxMoney<25000){
            tax=taxMoney*0.2;
        }else if(taxMoney<35000){
            tax=taxMoney*0.25;
        }else if(taxMoney<55000){
            tax=taxMoney*0.3;
        }else if(taxMoney<80000){
            tax=taxMoney*0.35;
        }else {
            tax=taxMoney*0.45;
        }
        return tax;
    }

    /**
     * 7.“应发工资-五险一金个人缴纳部分-个税”的结果填入“企业人员月度工资成本支付表.xlsx“的“实发工资”一栏。
     * @return 实发工资
     */
    public double getShiFaGongZi(){
        return getYingFaGongZi()-getSelf_WXYJ_All()-getTax();
    }

    /**
     * 获取到个人五险一金总共金额
     * @return 个人五险一金总共金额
     */
    public double getSelf_WXYJ_All(){
        return getYangLaoBaoXianSelf()+ //养老保险
                getYiLiaoBaoXianSelf()+ //医疗保险
                getShiYeBaoXianSelf()+ //失业保险
                getGongShangBaoXianSelf()+ //工伤保险
                getShengYuBaoXianSelf()+ //生育保险
                getGongJiJinSelf(); //住房公积金
    }

    /**
     * 获取到个人五险一金总共金额
     * @return 个人五险一金总共金额
     */
    public double getCompany_WXYJ_All(){
        return getYangLaoBaoXianCompany()+ //养老保险
                getYiLiaoBaoXianCompany()+ //医疗保险
                getShiYeBaoXianCompany()+ //失业保险
                getGongShangBaoXianCompany()+ //工伤保险
                getShengYuBaoXianCompany()+ //生育保险
                getGongJiJinCompany(); //住房公积金
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getYangLaoBaoXianSelf() {
        return yangLaoBaoXianSelf;
    }

    public void setYangLaoBaoXianSelf(double yangLaoBaoXianSelf) {
        this.yangLaoBaoXianSelf = yangLaoBaoXianSelf;
    }

    public double getYiLiaoBaoXianSelf() {
        return yiLiaoBaoXianSelf;
    }

    public void setYiLiaoBaoXianSelf(double yiLiaoBaoXianSelf) {
        this.yiLiaoBaoXianSelf = yiLiaoBaoXianSelf;
    }

    public double getShiYeBaoXianSelf() {
        return shiYeBaoXianSelf;
    }

    public void setShiYeBaoXianSelf(double shiYeBaoXianSelf) {
        this.shiYeBaoXianSelf = shiYeBaoXianSelf;
    }

    public double getGongShangBaoXianSelf() {
        return gongShangBaoXianSelf;
    }

    public void setGongShangBaoXianSelf(double gongShangBaoXianSelf) {
        this.gongShangBaoXianSelf = gongShangBaoXianSelf;
    }

    public double getShengYuBaoXianSelf() {
        return shengYuBaoXianSelf;
    }

    public void setShengYuBaoXianSelf(double shengYuBaoXianSelf) {
        this.shengYuBaoXianSelf = shengYuBaoXianSelf;
    }

    public double getGongJiJinSelf() {
        return gongJiJinSelf;
    }

    public void setGongJiJinSelf(double gongJiJinSelf) {
        this.gongJiJinSelf = gongJiJinSelf;
    }

    public double getYangLaoBaoXianCompany() {
        return yangLaoBaoXianCompany;
    }

    public void setYangLaoBaoXianCompany(double yangLaoBaoXianCompany) {
        this.yangLaoBaoXianCompany = yangLaoBaoXianCompany;
    }

    public double getYiLiaoBaoXianCompany() {
        return yiLiaoBaoXianCompany;
    }

    public void setYiLiaoBaoXianCompany(double yiLiaoBaoXianCompany) {
        this.yiLiaoBaoXianCompany = yiLiaoBaoXianCompany;
    }

    public double getShiYeBaoXianCompany() {
        return shiYeBaoXianCompany;
    }

    public void setShiYeBaoXianCompany(double shiYeBaoXianCompany) {
        this.shiYeBaoXianCompany = shiYeBaoXianCompany;
    }

    public double getGongShangBaoXianCompany() {
        return gongShangBaoXianCompany;
    }

    public void setGongShangBaoXianCompany(double gongShangBaoXianCompany) {
        this.gongShangBaoXianCompany = gongShangBaoXianCompany;
    }

    public double getShengYuBaoXianCompany() {
        return shengYuBaoXianCompany;
    }

    public void setShengYuBaoXianCompany(double shengYuBaoXianCompany) {
        this.shengYuBaoXianCompany = shengYuBaoXianCompany;
    }

    public double getGongJiJinCompany() {
        return gongJiJinCompany;
    }

    public void setGongJiJinCompany(double gongJiJinCompany) {
        this.gongJiJinCompany = gongJiJinCompany;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiXing() {
        return diXing;
    }

    public void setDiXing(double diXing) {
        this.diXing = diXing;
    }

    public double getGangWeiGonGzi() {
        return gangWeiGonGzi;
    }

    public void setGangWeiGonGzi(double gangWeiGonGzi) {
        this.gangWeiGonGzi = gangWeiGonGzi;
    }

    public double getJiXiaoJiangJin() {
        return jiXiaoJiangJin;
    }

    public void setJiXiaoJiangJin(double jiXiaoJiangJin) {
        this.jiXiaoJiangJin = jiXiaoJiangJin;
    }

    public double getQuanQinJiangJin() {
        return quanQinJiangJin;
    }

    public void setQuanQinJiangJin(double quanQinJiangJin) {
        this.quanQinJiangJin = quanQinJiangJin;
    }

    public double getKaoQinKoChu() {
        return kaoQinKoChu;
    }

    public void setKaoQinKoChu(double kaoQinKoChu) {
        this.kaoQinKoChu = kaoQinKoChu;
    }

    public double getWeiGuiChuFa() {
        return weiGuiChuFa;
    }

    public void setWeiGuiChuFa(double weiGuiChuFa) {
        this.weiGuiChuFa = weiGuiChuFa;
    }

    public double getJiaoTongBuZhu() {
        return jiaoTongBuZhu;
    }

    public void setJiaoTongBuZhu(double jiaoTongBuZhu) {
        this.jiaoTongBuZhu = jiaoTongBuZhu;
    }

    public double getTongXinBuZhu() {
        return tongXinBuZhu;
    }

    public void setTongXinBuZhu(double tongXinBuZhu) {
        this.tongXinBuZhu = tongXinBuZhu;
    }

    @Override
    public String toString() {
        return "StaffSalary{" +
                "工号:'" + id + '\'' +
                ", 姓名:'" + name + '\'' +
                ", 工资:'" + getGongzi() + '\'' +
                ", 扣款:" + getKokuan() +
                ", 养老(个人):" + getYangLaoBaoXianSelf() +
                ", 医疗(个人):" + getYiLiaoBaoXianSelf() +
                ", 失业(个人):" + getShiYeBaoXianSelf() +
                ", 工伤(个人):" + getGongShangBaoXianSelf() +
                ", 生育(个人):" + getShengYuBaoXianSelf() +
                ", 公积金(个人):" + getGongJiJinSelf() +
                ", 合计(个人):" + getSelf_WXYJ_All() +
                ", 养老(公司):" + getYangLaoBaoXianCompany() +
                ", 医疗(公司):" + getYiLiaoBaoXianCompany() +
                ", 失业(公司):" + getShiYeBaoXianCompany() +
                ", 工伤(公司):" + getGongShangBaoXianCompany() +
                ", 生育(公司):" + getShengYuBaoXianCompany() +
                ", 公积金(公司):" + getGongJiJinCompany() +
                ", 合计(公司):" + getCompany_WXYJ_All() +
                ", 个税金额:" + getTax() +
                ", 应发工资:" + getYingFaGongZi() +
                ", 实发工资:" + getShiFaGongZi() +
                ", 企业支出成本:" + getCompany() +
                '}';
    }
}
