package jcode18.community.model;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.id
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.name
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.accountId
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    private String accountid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.token
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    private String token;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.gmtCreate
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    private Long gmtcreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.gmtModified
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    private Long gmtmodified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.avatarUrl
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    private String avatarurl;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.id
     *
     * @return the value of user.id
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.id
     *
     * @param id the value for user.id
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.name
     *
     * @return the value of user.name
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.name
     *
     * @param name the value for user.name
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.accountId
     *
     * @return the value of user.accountId
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    public String getAccountid() {
        return accountid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.accountId
     *
     * @param accountid the value for user.accountId
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    public void setAccountid(String accountid) {
        this.accountid = accountid == null ? null : accountid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.token
     *
     * @return the value of user.token
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.token
     *
     * @param token the value for user.token
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.gmtCreate
     *
     * @return the value of user.gmtCreate
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    public Long getGmtcreate() {
        return gmtcreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.gmtCreate
     *
     * @param gmtcreate the value for user.gmtCreate
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    public void setGmtcreate(Long gmtcreate) {
        this.gmtcreate = gmtcreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.gmtModified
     *
     * @return the value of user.gmtModified
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    public Long getGmtmodified() {
        return gmtmodified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.gmtModified
     *
     * @param gmtmodified the value for user.gmtModified
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    public void setGmtmodified(Long gmtmodified) {
        this.gmtmodified = gmtmodified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.avatarUrl
     *
     * @return the value of user.avatarUrl
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    public String getAvatarurl() {
        return avatarurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.avatarUrl
     *
     * @param avatarurl the value for user.avatarUrl
     *
     * @mbg.generated Wed Feb 05 18:19:17 CST 2020
     */
    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl == null ? null : avatarurl.trim();
    }
}