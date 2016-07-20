package com.thinksky.net.rpc.model;

import java.util.List;

/**
 * Created by jiao on 2016/5/23.
 */
public class CardQueryModel {
  /**
   * accounts : [{"accountId":4,"accountName":"1234567890123456789","accountType":"1","accountUserName":"Had skill","bankName":"中国银行","bankNameCode":"","inputTime":1463707906000,"userId":"4"},{"accountId":5,"accountName":"9876543211234567890","accountType":"1","accountUserName":"Hsajdhkjhajkdjkjkds","bankName":"建设银行","bankNameCode":"","inputTime":1463741568000,"userId":"4"}]
   * money : 161
   */
  /**
   * data : {"accounts":[{"accountId":4,"accountName":"1234567890123456789","accountType":"1","accountUserName":"Had skill","bankName":"中国银行","bankNameCode":"","inputTime":1463707906000,"userId":"4"},{"accountId":5,"accountName":"9876543211234567890","accountType":"1","accountUserName":"Hsajdhkjhajkdjkjkds","bankName":"建设银行","bankNameCode":"","inputTime":1463741568000,"userId":"4"}],"money":161}
   * message : 成功
   * result : 1
   * token :
   */

    private int money;
    /**
     * accountId : 4
     * accountName : 1234567890123456789
     * accountType : 1
     * accountUserName : Had skill
     * bankName : 中国银行
     * bankNameCode :
     * inputTime : 1463707906000
     * userId : 4
     */

    private List<AccountsEntity> accounts;

    public int getMoney() {
      return money;
    }

    public void setMoney(int money) {
      this.money = money;
    }

    public List<AccountsEntity> getAccounts() {
      return accounts;
    }

    public void setAccounts(List<AccountsEntity> accounts) {
      this.accounts = accounts;
    }

    public static class AccountsEntity {
      private int accountId;
      private String accountName;
      private String accountType;
      private String accountUserName;
      private String bankName;
      private String bankNameCode;
      private long inputTime;
      private String userId;

      public int getAccountId() {
        return accountId;
      }

      public void setAccountId(int accountId) {
        this.accountId = accountId;
      }

      public String getAccountName() {
        return accountName;
      }

      public void setAccountName(String accountName) {
        this.accountName = accountName;
      }

      public String getAccountType() {
        return accountType;
      }

      public void setAccountType(String accountType) {
        this.accountType = accountType;
      }

      public String getAccountUserName() {
        return accountUserName;
      }

      public void setAccountUserName(String accountUserName) {
        this.accountUserName = accountUserName;
      }

      public String getBankName() {
        return bankName;
      }

      public void setBankName(String bankName) {
        this.bankName = bankName;
      }

      public String getBankNameCode() {
        return bankNameCode;
      }

      public void setBankNameCode(String bankNameCode) {
        this.bankNameCode = bankNameCode;
      }

      public long getInputTime() {
        return inputTime;
      }

      public void setInputTime(long inputTime) {
        this.inputTime = inputTime;
      }

      public String getUserId() {
        return userId;
      }

      public void setUserId(String userId) {
        this.userId = userId;
      }
    }

}
