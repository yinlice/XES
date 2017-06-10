package com.wxzt.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DddcdrExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DddcdrExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUuidIsNull() {
            addCriterion("uuid is null");
            return (Criteria) this;
        }

        public Criteria andUuidIsNotNull() {
            addCriterion("uuid is not null");
            return (Criteria) this;
        }

        public Criteria andUuidEqualTo(String value) {
            addCriterion("uuid =", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotEqualTo(String value) {
            addCriterion("uuid <>", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThan(String value) {
            addCriterion("uuid >", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThanOrEqualTo(String value) {
            addCriterion("uuid >=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThan(String value) {
            addCriterion("uuid <", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThanOrEqualTo(String value) {
            addCriterion("uuid <=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLike(String value) {
            addCriterion("uuid like", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotLike(String value) {
            addCriterion("uuid not like", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidIn(List<String> values) {
            addCriterion("uuid in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotIn(List<String> values) {
            addCriterion("uuid not in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidBetween(String value1, String value2) {
            addCriterion("uuid between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotBetween(String value1, String value2) {
            addCriterion("uuid not between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andSipnumIsNull() {
            addCriterion("sipnum is null");
            return (Criteria) this;
        }

        public Criteria andSipnumIsNotNull() {
            addCriterion("sipnum is not null");
            return (Criteria) this;
        }

        public Criteria andSipnumEqualTo(String value) {
            addCriterion("sipnum =", value, "sipnum");
            return (Criteria) this;
        }

        public Criteria andSipnumNotEqualTo(String value) {
            addCriterion("sipnum <>", value, "sipnum");
            return (Criteria) this;
        }

        public Criteria andSipnumGreaterThan(String value) {
            addCriterion("sipnum >", value, "sipnum");
            return (Criteria) this;
        }

        public Criteria andSipnumGreaterThanOrEqualTo(String value) {
            addCriterion("sipnum >=", value, "sipnum");
            return (Criteria) this;
        }

        public Criteria andSipnumLessThan(String value) {
            addCriterion("sipnum <", value, "sipnum");
            return (Criteria) this;
        }

        public Criteria andSipnumLessThanOrEqualTo(String value) {
            addCriterion("sipnum <=", value, "sipnum");
            return (Criteria) this;
        }

        public Criteria andSipnumLike(String value) {
            addCriterion("sipnum like", value, "sipnum");
            return (Criteria) this;
        }

        public Criteria andSipnumNotLike(String value) {
            addCriterion("sipnum not like", value, "sipnum");
            return (Criteria) this;
        }

        public Criteria andSipnumIn(List<String> values) {
            addCriterion("sipnum in", values, "sipnum");
            return (Criteria) this;
        }

        public Criteria andSipnumNotIn(List<String> values) {
            addCriterion("sipnum not in", values, "sipnum");
            return (Criteria) this;
        }

        public Criteria andSipnumBetween(String value1, String value2) {
            addCriterion("sipnum between", value1, value2, "sipnum");
            return (Criteria) this;
        }

        public Criteria andSipnumNotBetween(String value1, String value2) {
            addCriterion("sipnum not between", value1, value2, "sipnum");
            return (Criteria) this;
        }

        public Criteria andTelnumIsNull() {
            addCriterion("telnum is null");
            return (Criteria) this;
        }

        public Criteria andTelnumIsNotNull() {
            addCriterion("telnum is not null");
            return (Criteria) this;
        }

        public Criteria andTelnumEqualTo(String value) {
            addCriterion("telnum =", value, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumNotEqualTo(String value) {
            addCriterion("telnum <>", value, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumGreaterThan(String value) {
            addCriterion("telnum >", value, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumGreaterThanOrEqualTo(String value) {
            addCriterion("telnum >=", value, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumLessThan(String value) {
            addCriterion("telnum <", value, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumLessThanOrEqualTo(String value) {
            addCriterion("telnum <=", value, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumLike(String value) {
            addCriterion("telnum like", value, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumNotLike(String value) {
            addCriterion("telnum not like", value, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumIn(List<String> values) {
            addCriterion("telnum in", values, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumNotIn(List<String> values) {
            addCriterion("telnum not in", values, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumBetween(String value1, String value2) {
            addCriterion("telnum between", value1, value2, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumNotBetween(String value1, String value2) {
            addCriterion("telnum not between", value1, value2, "telnum");
            return (Criteria) this;
        }

        public Criteria andDirectionIsNull() {
            addCriterion("Direction is null");
            return (Criteria) this;
        }

        public Criteria andDirectionIsNotNull() {
            addCriterion("Direction is not null");
            return (Criteria) this;
        }

        public Criteria andDirectionEqualTo(String value) {
            addCriterion("Direction =", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotEqualTo(String value) {
            addCriterion("Direction <>", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionGreaterThan(String value) {
            addCriterion("Direction >", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionGreaterThanOrEqualTo(String value) {
            addCriterion("Direction >=", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionLessThan(String value) {
            addCriterion("Direction <", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionLessThanOrEqualTo(String value) {
            addCriterion("Direction <=", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionLike(String value) {
            addCriterion("Direction like", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotLike(String value) {
            addCriterion("Direction not like", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionIn(List<String> values) {
            addCriterion("Direction in", values, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotIn(List<String> values) {
            addCriterion("Direction not in", values, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionBetween(String value1, String value2) {
            addCriterion("Direction between", value1, value2, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotBetween(String value1, String value2) {
            addCriterion("Direction not between", value1, value2, "direction");
            return (Criteria) this;
        }

        public Criteria andCalllenIsNull() {
            addCriterion("calllen is null");
            return (Criteria) this;
        }

        public Criteria andCalllenIsNotNull() {
            addCriterion("calllen is not null");
            return (Criteria) this;
        }

        public Criteria andCalllenEqualTo(Integer value) {
            addCriterion("calllen =", value, "calllen");
            return (Criteria) this;
        }

        public Criteria andCalllenNotEqualTo(Integer value) {
            addCriterion("calllen <>", value, "calllen");
            return (Criteria) this;
        }

        public Criteria andCalllenGreaterThan(Integer value) {
            addCriterion("calllen >", value, "calllen");
            return (Criteria) this;
        }

        public Criteria andCalllenGreaterThanOrEqualTo(Integer value) {
            addCriterion("calllen >=", value, "calllen");
            return (Criteria) this;
        }

        public Criteria andCalllenLessThan(Integer value) {
            addCriterion("calllen <", value, "calllen");
            return (Criteria) this;
        }

        public Criteria andCalllenLessThanOrEqualTo(Integer value) {
            addCriterion("calllen <=", value, "calllen");
            return (Criteria) this;
        }

        public Criteria andCalllenIn(List<Integer> values) {
            addCriterion("calllen in", values, "calllen");
            return (Criteria) this;
        }

        public Criteria andCalllenNotIn(List<Integer> values) {
            addCriterion("calllen not in", values, "calllen");
            return (Criteria) this;
        }

        public Criteria andCalllenBetween(Integer value1, Integer value2) {
            addCriterion("calllen between", value1, value2, "calllen");
            return (Criteria) this;
        }

        public Criteria andCalllenNotBetween(Integer value1, Integer value2) {
            addCriterion("calllen not between", value1, value2, "calllen");
            return (Criteria) this;
        }

        public Criteria andDtstartIsNull() {
            addCriterion("dtstart is null");
            return (Criteria) this;
        }

        public Criteria andDtstartIsNotNull() {
            addCriterion("dtstart is not null");
            return (Criteria) this;
        }

        public Criteria andDtstartEqualTo(Date value) {
            addCriterion("dtstart =", value, "dtstart");
            return (Criteria) this;
        }

        public Criteria andDtstartNotEqualTo(Date value) {
            addCriterion("dtstart <>", value, "dtstart");
            return (Criteria) this;
        }

        public Criteria andDtstartGreaterThan(Date value) {
            addCriterion("dtstart >", value, "dtstart");
            return (Criteria) this;
        }

        public Criteria andDtstartGreaterThanOrEqualTo(Date value) {
            addCriterion("dtstart >=", value, "dtstart");
            return (Criteria) this;
        }

        public Criteria andDtstartLessThan(Date value) {
            addCriterion("dtstart <", value, "dtstart");
            return (Criteria) this;
        }

        public Criteria andDtstartLessThanOrEqualTo(Date value) {
            addCriterion("dtstart <=", value, "dtstart");
            return (Criteria) this;
        }

        public Criteria andDtstartIn(List<Date> values) {
            addCriterion("dtstart in", values, "dtstart");
            return (Criteria) this;
        }

        public Criteria andDtstartNotIn(List<Date> values) {
            addCriterion("dtstart not in", values, "dtstart");
            return (Criteria) this;
        }

        public Criteria andDtstartBetween(Date value1, Date value2) {
            addCriterion("dtstart between", value1, value2, "dtstart");
            return (Criteria) this;
        }

        public Criteria andDtstartNotBetween(Date value1, Date value2) {
            addCriterion("dtstart not between", value1, value2, "dtstart");
            return (Criteria) this;
        }

        public Criteria andDtendIsNull() {
            addCriterion("dtend is null");
            return (Criteria) this;
        }

        public Criteria andDtendIsNotNull() {
            addCriterion("dtend is not null");
            return (Criteria) this;
        }

        public Criteria andDtendEqualTo(Date value) {
            addCriterion("dtend =", value, "dtend");
            return (Criteria) this;
        }

        public Criteria andDtendNotEqualTo(Date value) {
            addCriterion("dtend <>", value, "dtend");
            return (Criteria) this;
        }

        public Criteria andDtendGreaterThan(Date value) {
            addCriterion("dtend >", value, "dtend");
            return (Criteria) this;
        }

        public Criteria andDtendGreaterThanOrEqualTo(Date value) {
            addCriterion("dtend >=", value, "dtend");
            return (Criteria) this;
        }

        public Criteria andDtendLessThan(Date value) {
            addCriterion("dtend <", value, "dtend");
            return (Criteria) this;
        }

        public Criteria andDtendLessThanOrEqualTo(Date value) {
            addCriterion("dtend <=", value, "dtend");
            return (Criteria) this;
        }

        public Criteria andDtendIn(List<Date> values) {
            addCriterion("dtend in", values, "dtend");
            return (Criteria) this;
        }

        public Criteria andDtendNotIn(List<Date> values) {
            addCriterion("dtend not in", values, "dtend");
            return (Criteria) this;
        }

        public Criteria andDtendBetween(Date value1, Date value2) {
            addCriterion("dtend between", value1, value2, "dtend");
            return (Criteria) this;
        }

        public Criteria andDtendNotBetween(Date value1, Date value2) {
            addCriterion("dtend not between", value1, value2, "dtend");
            return (Criteria) this;
        }

        public Criteria andCallretIsNull() {
            addCriterion("callret is null");
            return (Criteria) this;
        }

        public Criteria andCallretIsNotNull() {
            addCriterion("callret is not null");
            return (Criteria) this;
        }

        public Criteria andCallretEqualTo(String value) {
            addCriterion("callret =", value, "callret");
            return (Criteria) this;
        }

        public Criteria andCallretNotEqualTo(String value) {
            addCriterion("callret <>", value, "callret");
            return (Criteria) this;
        }

        public Criteria andCallretGreaterThan(String value) {
            addCriterion("callret >", value, "callret");
            return (Criteria) this;
        }

        public Criteria andCallretGreaterThanOrEqualTo(String value) {
            addCriterion("callret >=", value, "callret");
            return (Criteria) this;
        }

        public Criteria andCallretLessThan(String value) {
            addCriterion("callret <", value, "callret");
            return (Criteria) this;
        }

        public Criteria andCallretLessThanOrEqualTo(String value) {
            addCriterion("callret <=", value, "callret");
            return (Criteria) this;
        }

        public Criteria andCallretLike(String value) {
            addCriterion("callret like", value, "callret");
            return (Criteria) this;
        }

        public Criteria andCallretNotLike(String value) {
            addCriterion("callret not like", value, "callret");
            return (Criteria) this;
        }

        public Criteria andCallretIn(List<String> values) {
            addCriterion("callret in", values, "callret");
            return (Criteria) this;
        }

        public Criteria andCallretNotIn(List<String> values) {
            addCriterion("callret not in", values, "callret");
            return (Criteria) this;
        }

        public Criteria andCallretBetween(String value1, String value2) {
            addCriterion("callret between", value1, value2, "callret");
            return (Criteria) this;
        }

        public Criteria andCallretNotBetween(String value1, String value2) {
            addCriterion("callret not between", value1, value2, "callret");
            return (Criteria) this;
        }

        public Criteria andRecfilenameIsNull() {
            addCriterion("recfilename is null");
            return (Criteria) this;
        }

        public Criteria andRecfilenameIsNotNull() {
            addCriterion("recfilename is not null");
            return (Criteria) this;
        }

        public Criteria andRecfilenameEqualTo(String value) {
            addCriterion("recfilename =", value, "recfilename");
            return (Criteria) this;
        }

        public Criteria andRecfilenameNotEqualTo(String value) {
            addCriterion("recfilename <>", value, "recfilename");
            return (Criteria) this;
        }

        public Criteria andRecfilenameGreaterThan(String value) {
            addCriterion("recfilename >", value, "recfilename");
            return (Criteria) this;
        }

        public Criteria andRecfilenameGreaterThanOrEqualTo(String value) {
            addCriterion("recfilename >=", value, "recfilename");
            return (Criteria) this;
        }

        public Criteria andRecfilenameLessThan(String value) {
            addCriterion("recfilename <", value, "recfilename");
            return (Criteria) this;
        }

        public Criteria andRecfilenameLessThanOrEqualTo(String value) {
            addCriterion("recfilename <=", value, "recfilename");
            return (Criteria) this;
        }

        public Criteria andRecfilenameLike(String value) {
            addCriterion("recfilename like", value, "recfilename");
            return (Criteria) this;
        }

        public Criteria andRecfilenameNotLike(String value) {
            addCriterion("recfilename not like", value, "recfilename");
            return (Criteria) this;
        }

        public Criteria andRecfilenameIn(List<String> values) {
            addCriterion("recfilename in", values, "recfilename");
            return (Criteria) this;
        }

        public Criteria andRecfilenameNotIn(List<String> values) {
            addCriterion("recfilename not in", values, "recfilename");
            return (Criteria) this;
        }

        public Criteria andRecfilenameBetween(String value1, String value2) {
            addCriterion("recfilename between", value1, value2, "recfilename");
            return (Criteria) this;
        }

        public Criteria andRecfilenameNotBetween(String value1, String value2) {
            addCriterion("recfilename not between", value1, value2, "recfilename");
            return (Criteria) this;
        }

        public Criteria andWorknumIsNull() {
            addCriterion("Worknum is null");
            return (Criteria) this;
        }

        public Criteria andWorknumIsNotNull() {
            addCriterion("Worknum is not null");
            return (Criteria) this;
        }

        public Criteria andWorknumEqualTo(String value) {
            addCriterion("Worknum =", value, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumNotEqualTo(String value) {
            addCriterion("Worknum <>", value, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumGreaterThan(String value) {
            addCriterion("Worknum >", value, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumGreaterThanOrEqualTo(String value) {
            addCriterion("Worknum >=", value, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumLessThan(String value) {
            addCriterion("Worknum <", value, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumLessThanOrEqualTo(String value) {
            addCriterion("Worknum <=", value, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumLike(String value) {
            addCriterion("Worknum like", value, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumNotLike(String value) {
            addCriterion("Worknum not like", value, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumIn(List<String> values) {
            addCriterion("Worknum in", values, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumNotIn(List<String> values) {
            addCriterion("Worknum not in", values, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumBetween(String value1, String value2) {
            addCriterion("Worknum between", value1, value2, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumNotBetween(String value1, String value2) {
            addCriterion("Worknum not between", value1, value2, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknameIsNull() {
            addCriterion("Workname is null");
            return (Criteria) this;
        }

        public Criteria andWorknameIsNotNull() {
            addCriterion("Workname is not null");
            return (Criteria) this;
        }

        public Criteria andWorknameEqualTo(String value) {
            addCriterion("Workname =", value, "workname");
            return (Criteria) this;
        }

        public Criteria andWorknameNotEqualTo(String value) {
            addCriterion("Workname <>", value, "workname");
            return (Criteria) this;
        }

        public Criteria andWorknameGreaterThan(String value) {
            addCriterion("Workname >", value, "workname");
            return (Criteria) this;
        }

        public Criteria andWorknameGreaterThanOrEqualTo(String value) {
            addCriterion("Workname >=", value, "workname");
            return (Criteria) this;
        }

        public Criteria andWorknameLessThan(String value) {
            addCriterion("Workname <", value, "workname");
            return (Criteria) this;
        }

        public Criteria andWorknameLessThanOrEqualTo(String value) {
            addCriterion("Workname <=", value, "workname");
            return (Criteria) this;
        }

        public Criteria andWorknameLike(String value) {
            addCriterion("Workname like", value, "workname");
            return (Criteria) this;
        }

        public Criteria andWorknameNotLike(String value) {
            addCriterion("Workname not like", value, "workname");
            return (Criteria) this;
        }

        public Criteria andWorknameIn(List<String> values) {
            addCriterion("Workname in", values, "workname");
            return (Criteria) this;
        }

        public Criteria andWorknameNotIn(List<String> values) {
            addCriterion("Workname not in", values, "workname");
            return (Criteria) this;
        }

        public Criteria andWorknameBetween(String value1, String value2) {
            addCriterion("Workname between", value1, value2, "workname");
            return (Criteria) this;
        }

        public Criteria andWorknameNotBetween(String value1, String value2) {
            addCriterion("Workname not between", value1, value2, "workname");
            return (Criteria) this;
        }

        public Criteria andGroupnumIsNull() {
            addCriterion("Groupnum is null");
            return (Criteria) this;
        }

        public Criteria andGroupnumIsNotNull() {
            addCriterion("Groupnum is not null");
            return (Criteria) this;
        }

        public Criteria andGroupnumEqualTo(Integer value) {
            addCriterion("Groupnum =", value, "groupnum");
            return (Criteria) this;
        }

        public Criteria andGroupnumNotEqualTo(Integer value) {
            addCriterion("Groupnum <>", value, "groupnum");
            return (Criteria) this;
        }

        public Criteria andGroupnumGreaterThan(Integer value) {
            addCriterion("Groupnum >", value, "groupnum");
            return (Criteria) this;
        }

        public Criteria andGroupnumGreaterThanOrEqualTo(Integer value) {
            addCriterion("Groupnum >=", value, "groupnum");
            return (Criteria) this;
        }

        public Criteria andGroupnumLessThan(Integer value) {
            addCriterion("Groupnum <", value, "groupnum");
            return (Criteria) this;
        }

        public Criteria andGroupnumLessThanOrEqualTo(Integer value) {
            addCriterion("Groupnum <=", value, "groupnum");
            return (Criteria) this;
        }

        public Criteria andGroupnumIn(List<Integer> values) {
            addCriterion("Groupnum in", values, "groupnum");
            return (Criteria) this;
        }

        public Criteria andGroupnumNotIn(List<Integer> values) {
            addCriterion("Groupnum not in", values, "groupnum");
            return (Criteria) this;
        }

        public Criteria andGroupnumBetween(Integer value1, Integer value2) {
            addCriterion("Groupnum between", value1, value2, "groupnum");
            return (Criteria) this;
        }

        public Criteria andGroupnumNotBetween(Integer value1, Integer value2) {
            addCriterion("Groupnum not between", value1, value2, "groupnum");
            return (Criteria) this;
        }

        public Criteria andGroupnameIsNull() {
            addCriterion("Groupname is null");
            return (Criteria) this;
        }

        public Criteria andGroupnameIsNotNull() {
            addCriterion("Groupname is not null");
            return (Criteria) this;
        }

        public Criteria andGroupnameEqualTo(String value) {
            addCriterion("Groupname =", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameNotEqualTo(String value) {
            addCriterion("Groupname <>", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameGreaterThan(String value) {
            addCriterion("Groupname >", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameGreaterThanOrEqualTo(String value) {
            addCriterion("Groupname >=", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameLessThan(String value) {
            addCriterion("Groupname <", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameLessThanOrEqualTo(String value) {
            addCriterion("Groupname <=", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameLike(String value) {
            addCriterion("Groupname like", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameNotLike(String value) {
            addCriterion("Groupname not like", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameIn(List<String> values) {
            addCriterion("Groupname in", values, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameNotIn(List<String> values) {
            addCriterion("Groupname not in", values, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameBetween(String value1, String value2) {
            addCriterion("Groupname between", value1, value2, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameNotBetween(String value1, String value2) {
            addCriterion("Groupname not between", value1, value2, "groupname");
            return (Criteria) this;
        }

        public Criteria andCallouttaskidIsNull() {
            addCriterion("CalloutTaskID is null");
            return (Criteria) this;
        }

        public Criteria andCallouttaskidIsNotNull() {
            addCriterion("CalloutTaskID is not null");
            return (Criteria) this;
        }

        public Criteria andCallouttaskidEqualTo(Integer value) {
            addCriterion("CalloutTaskID =", value, "callouttaskid");
            return (Criteria) this;
        }

        public Criteria andCallouttaskidNotEqualTo(Integer value) {
            addCriterion("CalloutTaskID <>", value, "callouttaskid");
            return (Criteria) this;
        }

        public Criteria andCallouttaskidGreaterThan(Integer value) {
            addCriterion("CalloutTaskID >", value, "callouttaskid");
            return (Criteria) this;
        }

        public Criteria andCallouttaskidGreaterThanOrEqualTo(Integer value) {
            addCriterion("CalloutTaskID >=", value, "callouttaskid");
            return (Criteria) this;
        }

        public Criteria andCallouttaskidLessThan(Integer value) {
            addCriterion("CalloutTaskID <", value, "callouttaskid");
            return (Criteria) this;
        }

        public Criteria andCallouttaskidLessThanOrEqualTo(Integer value) {
            addCriterion("CalloutTaskID <=", value, "callouttaskid");
            return (Criteria) this;
        }

        public Criteria andCallouttaskidIn(List<Integer> values) {
            addCriterion("CalloutTaskID in", values, "callouttaskid");
            return (Criteria) this;
        }

        public Criteria andCallouttaskidNotIn(List<Integer> values) {
            addCriterion("CalloutTaskID not in", values, "callouttaskid");
            return (Criteria) this;
        }

        public Criteria andCallouttaskidBetween(Integer value1, Integer value2) {
            addCriterion("CalloutTaskID between", value1, value2, "callouttaskid");
            return (Criteria) this;
        }

        public Criteria andCallouttaskidNotBetween(Integer value1, Integer value2) {
            addCriterion("CalloutTaskID not between", value1, value2, "callouttaskid");
            return (Criteria) this;
        }

        public Criteria andCalloutidIsNull() {
            addCriterion("CalloutID is null");
            return (Criteria) this;
        }

        public Criteria andCalloutidIsNotNull() {
            addCriterion("CalloutID is not null");
            return (Criteria) this;
        }

        public Criteria andCalloutidEqualTo(Integer value) {
            addCriterion("CalloutID =", value, "calloutid");
            return (Criteria) this;
        }

        public Criteria andCalloutidNotEqualTo(Integer value) {
            addCriterion("CalloutID <>", value, "calloutid");
            return (Criteria) this;
        }

        public Criteria andCalloutidGreaterThan(Integer value) {
            addCriterion("CalloutID >", value, "calloutid");
            return (Criteria) this;
        }

        public Criteria andCalloutidGreaterThanOrEqualTo(Integer value) {
            addCriterion("CalloutID >=", value, "calloutid");
            return (Criteria) this;
        }

        public Criteria andCalloutidLessThan(Integer value) {
            addCriterion("CalloutID <", value, "calloutid");
            return (Criteria) this;
        }

        public Criteria andCalloutidLessThanOrEqualTo(Integer value) {
            addCriterion("CalloutID <=", value, "calloutid");
            return (Criteria) this;
        }

        public Criteria andCalloutidIn(List<Integer> values) {
            addCriterion("CalloutID in", values, "calloutid");
            return (Criteria) this;
        }

        public Criteria andCalloutidNotIn(List<Integer> values) {
            addCriterion("CalloutID not in", values, "calloutid");
            return (Criteria) this;
        }

        public Criteria andCalloutidBetween(Integer value1, Integer value2) {
            addCriterion("CalloutID between", value1, value2, "calloutid");
            return (Criteria) this;
        }

        public Criteria andCalloutidNotBetween(Integer value1, Integer value2) {
            addCriterion("CalloutID not between", value1, value2, "calloutid");
            return (Criteria) this;
        }

        public Criteria andBillsecIsNull() {
            addCriterion("billsec is null");
            return (Criteria) this;
        }

        public Criteria andBillsecIsNotNull() {
            addCriterion("billsec is not null");
            return (Criteria) this;
        }

        public Criteria andBillsecEqualTo(Integer value) {
            addCriterion("billsec =", value, "billsec");
            return (Criteria) this;
        }

        public Criteria andBillsecNotEqualTo(Integer value) {
            addCriterion("billsec <>", value, "billsec");
            return (Criteria) this;
        }

        public Criteria andBillsecGreaterThan(Integer value) {
            addCriterion("billsec >", value, "billsec");
            return (Criteria) this;
        }

        public Criteria andBillsecGreaterThanOrEqualTo(Integer value) {
            addCriterion("billsec >=", value, "billsec");
            return (Criteria) this;
        }

        public Criteria andBillsecLessThan(Integer value) {
            addCriterion("billsec <", value, "billsec");
            return (Criteria) this;
        }

        public Criteria andBillsecLessThanOrEqualTo(Integer value) {
            addCriterion("billsec <=", value, "billsec");
            return (Criteria) this;
        }

        public Criteria andBillsecIn(List<Integer> values) {
            addCriterion("billsec in", values, "billsec");
            return (Criteria) this;
        }

        public Criteria andBillsecNotIn(List<Integer> values) {
            addCriterion("billsec not in", values, "billsec");
            return (Criteria) this;
        }

        public Criteria andBillsecBetween(Integer value1, Integer value2) {
            addCriterion("billsec between", value1, value2, "billsec");
            return (Criteria) this;
        }

        public Criteria andBillsecNotBetween(Integer value1, Integer value2) {
            addCriterion("billsec not between", value1, value2, "billsec");
            return (Criteria) this;
        }

        public Criteria andProgresssecIsNull() {
            addCriterion("progresssec is null");
            return (Criteria) this;
        }

        public Criteria andProgresssecIsNotNull() {
            addCriterion("progresssec is not null");
            return (Criteria) this;
        }

        public Criteria andProgresssecEqualTo(Integer value) {
            addCriterion("progresssec =", value, "progresssec");
            return (Criteria) this;
        }

        public Criteria andProgresssecNotEqualTo(Integer value) {
            addCriterion("progresssec <>", value, "progresssec");
            return (Criteria) this;
        }

        public Criteria andProgresssecGreaterThan(Integer value) {
            addCriterion("progresssec >", value, "progresssec");
            return (Criteria) this;
        }

        public Criteria andProgresssecGreaterThanOrEqualTo(Integer value) {
            addCriterion("progresssec >=", value, "progresssec");
            return (Criteria) this;
        }

        public Criteria andProgresssecLessThan(Integer value) {
            addCriterion("progresssec <", value, "progresssec");
            return (Criteria) this;
        }

        public Criteria andProgresssecLessThanOrEqualTo(Integer value) {
            addCriterion("progresssec <=", value, "progresssec");
            return (Criteria) this;
        }

        public Criteria andProgresssecIn(List<Integer> values) {
            addCriterion("progresssec in", values, "progresssec");
            return (Criteria) this;
        }

        public Criteria andProgresssecNotIn(List<Integer> values) {
            addCriterion("progresssec not in", values, "progresssec");
            return (Criteria) this;
        }

        public Criteria andProgresssecBetween(Integer value1, Integer value2) {
            addCriterion("progresssec between", value1, value2, "progresssec");
            return (Criteria) this;
        }

        public Criteria andProgresssecNotBetween(Integer value1, Integer value2) {
            addCriterion("progresssec not between", value1, value2, "progresssec");
            return (Criteria) this;
        }

        public Criteria andAnswersecIsNull() {
            addCriterion("answersec is null");
            return (Criteria) this;
        }

        public Criteria andAnswersecIsNotNull() {
            addCriterion("answersec is not null");
            return (Criteria) this;
        }

        public Criteria andAnswersecEqualTo(Integer value) {
            addCriterion("answersec =", value, "answersec");
            return (Criteria) this;
        }

        public Criteria andAnswersecNotEqualTo(Integer value) {
            addCriterion("answersec <>", value, "answersec");
            return (Criteria) this;
        }

        public Criteria andAnswersecGreaterThan(Integer value) {
            addCriterion("answersec >", value, "answersec");
            return (Criteria) this;
        }

        public Criteria andAnswersecGreaterThanOrEqualTo(Integer value) {
            addCriterion("answersec >=", value, "answersec");
            return (Criteria) this;
        }

        public Criteria andAnswersecLessThan(Integer value) {
            addCriterion("answersec <", value, "answersec");
            return (Criteria) this;
        }

        public Criteria andAnswersecLessThanOrEqualTo(Integer value) {
            addCriterion("answersec <=", value, "answersec");
            return (Criteria) this;
        }

        public Criteria andAnswersecIn(List<Integer> values) {
            addCriterion("answersec in", values, "answersec");
            return (Criteria) this;
        }

        public Criteria andAnswersecNotIn(List<Integer> values) {
            addCriterion("answersec not in", values, "answersec");
            return (Criteria) this;
        }

        public Criteria andAnswersecBetween(Integer value1, Integer value2) {
            addCriterion("answersec between", value1, value2, "answersec");
            return (Criteria) this;
        }

        public Criteria andAnswersecNotBetween(Integer value1, Integer value2) {
            addCriterion("answersec not between", value1, value2, "answersec");
            return (Criteria) this;
        }

        public Criteria andWaitsecIsNull() {
            addCriterion("waitsec is null");
            return (Criteria) this;
        }

        public Criteria andWaitsecIsNotNull() {
            addCriterion("waitsec is not null");
            return (Criteria) this;
        }

        public Criteria andWaitsecEqualTo(Integer value) {
            addCriterion("waitsec =", value, "waitsec");
            return (Criteria) this;
        }

        public Criteria andWaitsecNotEqualTo(Integer value) {
            addCriterion("waitsec <>", value, "waitsec");
            return (Criteria) this;
        }

        public Criteria andWaitsecGreaterThan(Integer value) {
            addCriterion("waitsec >", value, "waitsec");
            return (Criteria) this;
        }

        public Criteria andWaitsecGreaterThanOrEqualTo(Integer value) {
            addCriterion("waitsec >=", value, "waitsec");
            return (Criteria) this;
        }

        public Criteria andWaitsecLessThan(Integer value) {
            addCriterion("waitsec <", value, "waitsec");
            return (Criteria) this;
        }

        public Criteria andWaitsecLessThanOrEqualTo(Integer value) {
            addCriterion("waitsec <=", value, "waitsec");
            return (Criteria) this;
        }

        public Criteria andWaitsecIn(List<Integer> values) {
            addCriterion("waitsec in", values, "waitsec");
            return (Criteria) this;
        }

        public Criteria andWaitsecNotIn(List<Integer> values) {
            addCriterion("waitsec not in", values, "waitsec");
            return (Criteria) this;
        }

        public Criteria andWaitsecBetween(Integer value1, Integer value2) {
            addCriterion("waitsec between", value1, value2, "waitsec");
            return (Criteria) this;
        }

        public Criteria andWaitsecNotBetween(Integer value1, Integer value2) {
            addCriterion("waitsec not between", value1, value2, "waitsec");
            return (Criteria) this;
        }

        public Criteria andProgressMediasecIsNull() {
            addCriterion("progress_mediasec is null");
            return (Criteria) this;
        }

        public Criteria andProgressMediasecIsNotNull() {
            addCriterion("progress_mediasec is not null");
            return (Criteria) this;
        }

        public Criteria andProgressMediasecEqualTo(Integer value) {
            addCriterion("progress_mediasec =", value, "progressMediasec");
            return (Criteria) this;
        }

        public Criteria andProgressMediasecNotEqualTo(Integer value) {
            addCriterion("progress_mediasec <>", value, "progressMediasec");
            return (Criteria) this;
        }

        public Criteria andProgressMediasecGreaterThan(Integer value) {
            addCriterion("progress_mediasec >", value, "progressMediasec");
            return (Criteria) this;
        }

        public Criteria andProgressMediasecGreaterThanOrEqualTo(Integer value) {
            addCriterion("progress_mediasec >=", value, "progressMediasec");
            return (Criteria) this;
        }

        public Criteria andProgressMediasecLessThan(Integer value) {
            addCriterion("progress_mediasec <", value, "progressMediasec");
            return (Criteria) this;
        }

        public Criteria andProgressMediasecLessThanOrEqualTo(Integer value) {
            addCriterion("progress_mediasec <=", value, "progressMediasec");
            return (Criteria) this;
        }

        public Criteria andProgressMediasecIn(List<Integer> values) {
            addCriterion("progress_mediasec in", values, "progressMediasec");
            return (Criteria) this;
        }

        public Criteria andProgressMediasecNotIn(List<Integer> values) {
            addCriterion("progress_mediasec not in", values, "progressMediasec");
            return (Criteria) this;
        }

        public Criteria andProgressMediasecBetween(Integer value1, Integer value2) {
            addCriterion("progress_mediasec between", value1, value2, "progressMediasec");
            return (Criteria) this;
        }

        public Criteria andProgressMediasecNotBetween(Integer value1, Integer value2) {
            addCriterion("progress_mediasec not between", value1, value2, "progressMediasec");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNull() {
            addCriterion("Province is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("Province is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(String value) {
            addCriterion("Province =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(String value) {
            addCriterion("Province <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(String value) {
            addCriterion("Province >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("Province >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(String value) {
            addCriterion("Province <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(String value) {
            addCriterion("Province <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLike(String value) {
            addCriterion("Province like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotLike(String value) {
            addCriterion("Province not like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<String> values) {
            addCriterion("Province in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<String> values) {
            addCriterion("Province not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(String value1, String value2) {
            addCriterion("Province between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(String value1, String value2) {
            addCriterion("Province not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("City is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("City is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("City =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("City <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("City >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("City >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("City <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("City <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("City like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("City not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("City in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("City not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("City between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("City not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andTeltypeIsNull() {
            addCriterion("TelType is null");
            return (Criteria) this;
        }

        public Criteria andTeltypeIsNotNull() {
            addCriterion("TelType is not null");
            return (Criteria) this;
        }

        public Criteria andTeltypeEqualTo(String value) {
            addCriterion("TelType =", value, "teltype");
            return (Criteria) this;
        }

        public Criteria andTeltypeNotEqualTo(String value) {
            addCriterion("TelType <>", value, "teltype");
            return (Criteria) this;
        }

        public Criteria andTeltypeGreaterThan(String value) {
            addCriterion("TelType >", value, "teltype");
            return (Criteria) this;
        }

        public Criteria andTeltypeGreaterThanOrEqualTo(String value) {
            addCriterion("TelType >=", value, "teltype");
            return (Criteria) this;
        }

        public Criteria andTeltypeLessThan(String value) {
            addCriterion("TelType <", value, "teltype");
            return (Criteria) this;
        }

        public Criteria andTeltypeLessThanOrEqualTo(String value) {
            addCriterion("TelType <=", value, "teltype");
            return (Criteria) this;
        }

        public Criteria andTeltypeLike(String value) {
            addCriterion("TelType like", value, "teltype");
            return (Criteria) this;
        }

        public Criteria andTeltypeNotLike(String value) {
            addCriterion("TelType not like", value, "teltype");
            return (Criteria) this;
        }

        public Criteria andTeltypeIn(List<String> values) {
            addCriterion("TelType in", values, "teltype");
            return (Criteria) this;
        }

        public Criteria andTeltypeNotIn(List<String> values) {
            addCriterion("TelType not in", values, "teltype");
            return (Criteria) this;
        }

        public Criteria andTeltypeBetween(String value1, String value2) {
            addCriterion("TelType between", value1, value2, "teltype");
            return (Criteria) this;
        }

        public Criteria andTeltypeNotBetween(String value1, String value2) {
            addCriterion("TelType not between", value1, value2, "teltype");
            return (Criteria) this;
        }

        public Criteria andCardtypeIsNull() {
            addCriterion("CardType is null");
            return (Criteria) this;
        }

        public Criteria andCardtypeIsNotNull() {
            addCriterion("CardType is not null");
            return (Criteria) this;
        }

        public Criteria andCardtypeEqualTo(String value) {
            addCriterion("CardType =", value, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeNotEqualTo(String value) {
            addCriterion("CardType <>", value, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeGreaterThan(String value) {
            addCriterion("CardType >", value, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeGreaterThanOrEqualTo(String value) {
            addCriterion("CardType >=", value, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeLessThan(String value) {
            addCriterion("CardType <", value, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeLessThanOrEqualTo(String value) {
            addCriterion("CardType <=", value, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeLike(String value) {
            addCriterion("CardType like", value, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeNotLike(String value) {
            addCriterion("CardType not like", value, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeIn(List<String> values) {
            addCriterion("CardType in", values, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeNotIn(List<String> values) {
            addCriterion("CardType not in", values, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeBetween(String value1, String value2) {
            addCriterion("CardType between", value1, value2, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeNotBetween(String value1, String value2) {
            addCriterion("CardType not between", value1, value2, "cardtype");
            return (Criteria) this;
        }

        public Criteria andHotlineIsNull() {
            addCriterion("HotLine is null");
            return (Criteria) this;
        }

        public Criteria andHotlineIsNotNull() {
            addCriterion("HotLine is not null");
            return (Criteria) this;
        }

        public Criteria andHotlineEqualTo(String value) {
            addCriterion("HotLine =", value, "hotline");
            return (Criteria) this;
        }

        public Criteria andHotlineNotEqualTo(String value) {
            addCriterion("HotLine <>", value, "hotline");
            return (Criteria) this;
        }

        public Criteria andHotlineGreaterThan(String value) {
            addCriterion("HotLine >", value, "hotline");
            return (Criteria) this;
        }

        public Criteria andHotlineGreaterThanOrEqualTo(String value) {
            addCriterion("HotLine >=", value, "hotline");
            return (Criteria) this;
        }

        public Criteria andHotlineLessThan(String value) {
            addCriterion("HotLine <", value, "hotline");
            return (Criteria) this;
        }

        public Criteria andHotlineLessThanOrEqualTo(String value) {
            addCriterion("HotLine <=", value, "hotline");
            return (Criteria) this;
        }

        public Criteria andHotlineLike(String value) {
            addCriterion("HotLine like", value, "hotline");
            return (Criteria) this;
        }

        public Criteria andHotlineNotLike(String value) {
            addCriterion("HotLine not like", value, "hotline");
            return (Criteria) this;
        }

        public Criteria andHotlineIn(List<String> values) {
            addCriterion("HotLine in", values, "hotline");
            return (Criteria) this;
        }

        public Criteria andHotlineNotIn(List<String> values) {
            addCriterion("HotLine not in", values, "hotline");
            return (Criteria) this;
        }

        public Criteria andHotlineBetween(String value1, String value2) {
            addCriterion("HotLine between", value1, value2, "hotline");
            return (Criteria) this;
        }

        public Criteria andHotlineNotBetween(String value1, String value2) {
            addCriterion("HotLine not between", value1, value2, "hotline");
            return (Criteria) this;
        }

        public Criteria andTickettypeIsNull() {
            addCriterion("TicketType is null");
            return (Criteria) this;
        }

        public Criteria andTickettypeIsNotNull() {
            addCriterion("TicketType is not null");
            return (Criteria) this;
        }

        public Criteria andTickettypeEqualTo(Integer value) {
            addCriterion("TicketType =", value, "tickettype");
            return (Criteria) this;
        }

        public Criteria andTickettypeNotEqualTo(Integer value) {
            addCriterion("TicketType <>", value, "tickettype");
            return (Criteria) this;
        }

        public Criteria andTickettypeGreaterThan(Integer value) {
            addCriterion("TicketType >", value, "tickettype");
            return (Criteria) this;
        }

        public Criteria andTickettypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("TicketType >=", value, "tickettype");
            return (Criteria) this;
        }

        public Criteria andTickettypeLessThan(Integer value) {
            addCriterion("TicketType <", value, "tickettype");
            return (Criteria) this;
        }

        public Criteria andTickettypeLessThanOrEqualTo(Integer value) {
            addCriterion("TicketType <=", value, "tickettype");
            return (Criteria) this;
        }

        public Criteria andTickettypeIn(List<Integer> values) {
            addCriterion("TicketType in", values, "tickettype");
            return (Criteria) this;
        }

        public Criteria andTickettypeNotIn(List<Integer> values) {
            addCriterion("TicketType not in", values, "tickettype");
            return (Criteria) this;
        }

        public Criteria andTickettypeBetween(Integer value1, Integer value2) {
            addCriterion("TicketType between", value1, value2, "tickettype");
            return (Criteria) this;
        }

        public Criteria andTickettypeNotBetween(Integer value1, Integer value2) {
            addCriterion("TicketType not between", value1, value2, "tickettype");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("Remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("Remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("Remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("Remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("Remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("Remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("Remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("Remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("Remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("Remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("Remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("Remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("Remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("Remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andIsreadIsNull() {
            addCriterion("IsRead is null");
            return (Criteria) this;
        }

        public Criteria andIsreadIsNotNull() {
            addCriterion("IsRead is not null");
            return (Criteria) this;
        }

        public Criteria andIsreadEqualTo(Integer value) {
            addCriterion("IsRead =", value, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadNotEqualTo(Integer value) {
            addCriterion("IsRead <>", value, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadGreaterThan(Integer value) {
            addCriterion("IsRead >", value, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadGreaterThanOrEqualTo(Integer value) {
            addCriterion("IsRead >=", value, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadLessThan(Integer value) {
            addCriterion("IsRead <", value, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadLessThanOrEqualTo(Integer value) {
            addCriterion("IsRead <=", value, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadIn(List<Integer> values) {
            addCriterion("IsRead in", values, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadNotIn(List<Integer> values) {
            addCriterion("IsRead not in", values, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadBetween(Integer value1, Integer value2) {
            addCriterion("IsRead between", value1, value2, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadNotBetween(Integer value1, Integer value2) {
            addCriterion("IsRead not between", value1, value2, "isread");
            return (Criteria) this;
        }

        public Criteria andDtcreateIsNull() {
            addCriterion("dtCreate is null");
            return (Criteria) this;
        }

        public Criteria andDtcreateIsNotNull() {
            addCriterion("dtCreate is not null");
            return (Criteria) this;
        }

        public Criteria andDtcreateEqualTo(Date value) {
            addCriterion("dtCreate =", value, "dtcreate");
            return (Criteria) this;
        }

        public Criteria andDtcreateNotEqualTo(Date value) {
            addCriterion("dtCreate <>", value, "dtcreate");
            return (Criteria) this;
        }

        public Criteria andDtcreateGreaterThan(Date value) {
            addCriterion("dtCreate >", value, "dtcreate");
            return (Criteria) this;
        }

        public Criteria andDtcreateGreaterThanOrEqualTo(Date value) {
            addCriterion("dtCreate >=", value, "dtcreate");
            return (Criteria) this;
        }

        public Criteria andDtcreateLessThan(Date value) {
            addCriterion("dtCreate <", value, "dtcreate");
            return (Criteria) this;
        }

        public Criteria andDtcreateLessThanOrEqualTo(Date value) {
            addCriterion("dtCreate <=", value, "dtcreate");
            return (Criteria) this;
        }

        public Criteria andDtcreateIn(List<Date> values) {
            addCriterion("dtCreate in", values, "dtcreate");
            return (Criteria) this;
        }

        public Criteria andDtcreateNotIn(List<Date> values) {
            addCriterion("dtCreate not in", values, "dtcreate");
            return (Criteria) this;
        }

        public Criteria andDtcreateBetween(Date value1, Date value2) {
            addCriterion("dtCreate between", value1, value2, "dtcreate");
            return (Criteria) this;
        }

        public Criteria andDtcreateNotBetween(Date value1, Date value2) {
            addCriterion("dtCreate not between", value1, value2, "dtcreate");
            return (Criteria) this;
        }

        public Criteria andDtacdstartIsNull() {
            addCriterion("dtacdStart is null");
            return (Criteria) this;
        }

        public Criteria andDtacdstartIsNotNull() {
            addCriterion("dtacdStart is not null");
            return (Criteria) this;
        }

        public Criteria andDtacdstartEqualTo(Date value) {
            addCriterion("dtacdStart =", value, "dtacdstart");
            return (Criteria) this;
        }

        public Criteria andDtacdstartNotEqualTo(Date value) {
            addCriterion("dtacdStart <>", value, "dtacdstart");
            return (Criteria) this;
        }

        public Criteria andDtacdstartGreaterThan(Date value) {
            addCriterion("dtacdStart >", value, "dtacdstart");
            return (Criteria) this;
        }

        public Criteria andDtacdstartGreaterThanOrEqualTo(Date value) {
            addCriterion("dtacdStart >=", value, "dtacdstart");
            return (Criteria) this;
        }

        public Criteria andDtacdstartLessThan(Date value) {
            addCriterion("dtacdStart <", value, "dtacdstart");
            return (Criteria) this;
        }

        public Criteria andDtacdstartLessThanOrEqualTo(Date value) {
            addCriterion("dtacdStart <=", value, "dtacdstart");
            return (Criteria) this;
        }

        public Criteria andDtacdstartIn(List<Date> values) {
            addCriterion("dtacdStart in", values, "dtacdstart");
            return (Criteria) this;
        }

        public Criteria andDtacdstartNotIn(List<Date> values) {
            addCriterion("dtacdStart not in", values, "dtacdstart");
            return (Criteria) this;
        }

        public Criteria andDtacdstartBetween(Date value1, Date value2) {
            addCriterion("dtacdStart between", value1, value2, "dtacdstart");
            return (Criteria) this;
        }

        public Criteria andDtacdstartNotBetween(Date value1, Date value2) {
            addCriterion("dtacdStart not between", value1, value2, "dtacdstart");
            return (Criteria) this;
        }

        public Criteria andDtringstartIsNull() {
            addCriterion("dtringStart is null");
            return (Criteria) this;
        }

        public Criteria andDtringstartIsNotNull() {
            addCriterion("dtringStart is not null");
            return (Criteria) this;
        }

        public Criteria andDtringstartEqualTo(Date value) {
            addCriterion("dtringStart =", value, "dtringstart");
            return (Criteria) this;
        }

        public Criteria andDtringstartNotEqualTo(Date value) {
            addCriterion("dtringStart <>", value, "dtringstart");
            return (Criteria) this;
        }

        public Criteria andDtringstartGreaterThan(Date value) {
            addCriterion("dtringStart >", value, "dtringstart");
            return (Criteria) this;
        }

        public Criteria andDtringstartGreaterThanOrEqualTo(Date value) {
            addCriterion("dtringStart >=", value, "dtringstart");
            return (Criteria) this;
        }

        public Criteria andDtringstartLessThan(Date value) {
            addCriterion("dtringStart <", value, "dtringstart");
            return (Criteria) this;
        }

        public Criteria andDtringstartLessThanOrEqualTo(Date value) {
            addCriterion("dtringStart <=", value, "dtringstart");
            return (Criteria) this;
        }

        public Criteria andDtringstartIn(List<Date> values) {
            addCriterion("dtringStart in", values, "dtringstart");
            return (Criteria) this;
        }

        public Criteria andDtringstartNotIn(List<Date> values) {
            addCriterion("dtringStart not in", values, "dtringstart");
            return (Criteria) this;
        }

        public Criteria andDtringstartBetween(Date value1, Date value2) {
            addCriterion("dtringStart between", value1, value2, "dtringstart");
            return (Criteria) this;
        }

        public Criteria andDtringstartNotBetween(Date value1, Date value2) {
            addCriterion("dtringStart not between", value1, value2, "dtringstart");
            return (Criteria) this;
        }

        public Criteria andDtspeakstartIsNull() {
            addCriterion("dtspeakStart is null");
            return (Criteria) this;
        }

        public Criteria andDtspeakstartIsNotNull() {
            addCriterion("dtspeakStart is not null");
            return (Criteria) this;
        }

        public Criteria andDtspeakstartEqualTo(Date value) {
            addCriterion("dtspeakStart =", value, "dtspeakstart");
            return (Criteria) this;
        }

        public Criteria andDtspeakstartNotEqualTo(Date value) {
            addCriterion("dtspeakStart <>", value, "dtspeakstart");
            return (Criteria) this;
        }

        public Criteria andDtspeakstartGreaterThan(Date value) {
            addCriterion("dtspeakStart >", value, "dtspeakstart");
            return (Criteria) this;
        }

        public Criteria andDtspeakstartGreaterThanOrEqualTo(Date value) {
            addCriterion("dtspeakStart >=", value, "dtspeakstart");
            return (Criteria) this;
        }

        public Criteria andDtspeakstartLessThan(Date value) {
            addCriterion("dtspeakStart <", value, "dtspeakstart");
            return (Criteria) this;
        }

        public Criteria andDtspeakstartLessThanOrEqualTo(Date value) {
            addCriterion("dtspeakStart <=", value, "dtspeakstart");
            return (Criteria) this;
        }

        public Criteria andDtspeakstartIn(List<Date> values) {
            addCriterion("dtspeakStart in", values, "dtspeakstart");
            return (Criteria) this;
        }

        public Criteria andDtspeakstartNotIn(List<Date> values) {
            addCriterion("dtspeakStart not in", values, "dtspeakstart");
            return (Criteria) this;
        }

        public Criteria andDtspeakstartBetween(Date value1, Date value2) {
            addCriterion("dtspeakStart between", value1, value2, "dtspeakstart");
            return (Criteria) this;
        }

        public Criteria andDtspeakstartNotBetween(Date value1, Date value2) {
            addCriterion("dtspeakStart not between", value1, value2, "dtspeakstart");
            return (Criteria) this;
        }

        public Criteria andDthangupIsNull() {
            addCriterion("dthangup is null");
            return (Criteria) this;
        }

        public Criteria andDthangupIsNotNull() {
            addCriterion("dthangup is not null");
            return (Criteria) this;
        }

        public Criteria andDthangupEqualTo(Date value) {
            addCriterion("dthangup =", value, "dthangup");
            return (Criteria) this;
        }

        public Criteria andDthangupNotEqualTo(Date value) {
            addCriterion("dthangup <>", value, "dthangup");
            return (Criteria) this;
        }

        public Criteria andDthangupGreaterThan(Date value) {
            addCriterion("dthangup >", value, "dthangup");
            return (Criteria) this;
        }

        public Criteria andDthangupGreaterThanOrEqualTo(Date value) {
            addCriterion("dthangup >=", value, "dthangup");
            return (Criteria) this;
        }

        public Criteria andDthangupLessThan(Date value) {
            addCriterion("dthangup <", value, "dthangup");
            return (Criteria) this;
        }

        public Criteria andDthangupLessThanOrEqualTo(Date value) {
            addCriterion("dthangup <=", value, "dthangup");
            return (Criteria) this;
        }

        public Criteria andDthangupIn(List<Date> values) {
            addCriterion("dthangup in", values, "dthangup");
            return (Criteria) this;
        }

        public Criteria andDthangupNotIn(List<Date> values) {
            addCriterion("dthangup not in", values, "dthangup");
            return (Criteria) this;
        }

        public Criteria andDthangupBetween(Date value1, Date value2) {
            addCriterion("dthangup between", value1, value2, "dthangup");
            return (Criteria) this;
        }

        public Criteria andDthangupNotBetween(Date value1, Date value2) {
            addCriterion("dthangup not between", value1, value2, "dthangup");
            return (Criteria) this;
        }

        public Criteria andTimelentotalcallIsNull() {
            addCriterion("TimelenTotalCall is null");
            return (Criteria) this;
        }

        public Criteria andTimelentotalcallIsNotNull() {
            addCriterion("TimelenTotalCall is not null");
            return (Criteria) this;
        }

        public Criteria andTimelentotalcallEqualTo(Integer value) {
            addCriterion("TimelenTotalCall =", value, "timelentotalcall");
            return (Criteria) this;
        }

        public Criteria andTimelentotalcallNotEqualTo(Integer value) {
            addCriterion("TimelenTotalCall <>", value, "timelentotalcall");
            return (Criteria) this;
        }

        public Criteria andTimelentotalcallGreaterThan(Integer value) {
            addCriterion("TimelenTotalCall >", value, "timelentotalcall");
            return (Criteria) this;
        }

        public Criteria andTimelentotalcallGreaterThanOrEqualTo(Integer value) {
            addCriterion("TimelenTotalCall >=", value, "timelentotalcall");
            return (Criteria) this;
        }

        public Criteria andTimelentotalcallLessThan(Integer value) {
            addCriterion("TimelenTotalCall <", value, "timelentotalcall");
            return (Criteria) this;
        }

        public Criteria andTimelentotalcallLessThanOrEqualTo(Integer value) {
            addCriterion("TimelenTotalCall <=", value, "timelentotalcall");
            return (Criteria) this;
        }

        public Criteria andTimelentotalcallIn(List<Integer> values) {
            addCriterion("TimelenTotalCall in", values, "timelentotalcall");
            return (Criteria) this;
        }

        public Criteria andTimelentotalcallNotIn(List<Integer> values) {
            addCriterion("TimelenTotalCall not in", values, "timelentotalcall");
            return (Criteria) this;
        }

        public Criteria andTimelentotalcallBetween(Integer value1, Integer value2) {
            addCriterion("TimelenTotalCall between", value1, value2, "timelentotalcall");
            return (Criteria) this;
        }

        public Criteria andTimelentotalcallNotBetween(Integer value1, Integer value2) {
            addCriterion("TimelenTotalCall not between", value1, value2, "timelentotalcall");
            return (Criteria) this;
        }

        public Criteria andTimelenivrIsNull() {
            addCriterion("TimelenIvr is null");
            return (Criteria) this;
        }

        public Criteria andTimelenivrIsNotNull() {
            addCriterion("TimelenIvr is not null");
            return (Criteria) this;
        }

        public Criteria andTimelenivrEqualTo(Integer value) {
            addCriterion("TimelenIvr =", value, "timelenivr");
            return (Criteria) this;
        }

        public Criteria andTimelenivrNotEqualTo(Integer value) {
            addCriterion("TimelenIvr <>", value, "timelenivr");
            return (Criteria) this;
        }

        public Criteria andTimelenivrGreaterThan(Integer value) {
            addCriterion("TimelenIvr >", value, "timelenivr");
            return (Criteria) this;
        }

        public Criteria andTimelenivrGreaterThanOrEqualTo(Integer value) {
            addCriterion("TimelenIvr >=", value, "timelenivr");
            return (Criteria) this;
        }

        public Criteria andTimelenivrLessThan(Integer value) {
            addCriterion("TimelenIvr <", value, "timelenivr");
            return (Criteria) this;
        }

        public Criteria andTimelenivrLessThanOrEqualTo(Integer value) {
            addCriterion("TimelenIvr <=", value, "timelenivr");
            return (Criteria) this;
        }

        public Criteria andTimelenivrIn(List<Integer> values) {
            addCriterion("TimelenIvr in", values, "timelenivr");
            return (Criteria) this;
        }

        public Criteria andTimelenivrNotIn(List<Integer> values) {
            addCriterion("TimelenIvr not in", values, "timelenivr");
            return (Criteria) this;
        }

        public Criteria andTimelenivrBetween(Integer value1, Integer value2) {
            addCriterion("TimelenIvr between", value1, value2, "timelenivr");
            return (Criteria) this;
        }

        public Criteria andTimelenivrNotBetween(Integer value1, Integer value2) {
            addCriterion("TimelenIvr not between", value1, value2, "timelenivr");
            return (Criteria) this;
        }

        public Criteria andTimelenacdIsNull() {
            addCriterion("TimelenAcd is null");
            return (Criteria) this;
        }

        public Criteria andTimelenacdIsNotNull() {
            addCriterion("TimelenAcd is not null");
            return (Criteria) this;
        }

        public Criteria andTimelenacdEqualTo(Integer value) {
            addCriterion("TimelenAcd =", value, "timelenacd");
            return (Criteria) this;
        }

        public Criteria andTimelenacdNotEqualTo(Integer value) {
            addCriterion("TimelenAcd <>", value, "timelenacd");
            return (Criteria) this;
        }

        public Criteria andTimelenacdGreaterThan(Integer value) {
            addCriterion("TimelenAcd >", value, "timelenacd");
            return (Criteria) this;
        }

        public Criteria andTimelenacdGreaterThanOrEqualTo(Integer value) {
            addCriterion("TimelenAcd >=", value, "timelenacd");
            return (Criteria) this;
        }

        public Criteria andTimelenacdLessThan(Integer value) {
            addCriterion("TimelenAcd <", value, "timelenacd");
            return (Criteria) this;
        }

        public Criteria andTimelenacdLessThanOrEqualTo(Integer value) {
            addCriterion("TimelenAcd <=", value, "timelenacd");
            return (Criteria) this;
        }

        public Criteria andTimelenacdIn(List<Integer> values) {
            addCriterion("TimelenAcd in", values, "timelenacd");
            return (Criteria) this;
        }

        public Criteria andTimelenacdNotIn(List<Integer> values) {
            addCriterion("TimelenAcd not in", values, "timelenacd");
            return (Criteria) this;
        }

        public Criteria andTimelenacdBetween(Integer value1, Integer value2) {
            addCriterion("TimelenAcd between", value1, value2, "timelenacd");
            return (Criteria) this;
        }

        public Criteria andTimelenacdNotBetween(Integer value1, Integer value2) {
            addCriterion("TimelenAcd not between", value1, value2, "timelenacd");
            return (Criteria) this;
        }

        public Criteria andTimelenringIsNull() {
            addCriterion("TimelenRing is null");
            return (Criteria) this;
        }

        public Criteria andTimelenringIsNotNull() {
            addCriterion("TimelenRing is not null");
            return (Criteria) this;
        }

        public Criteria andTimelenringEqualTo(Integer value) {
            addCriterion("TimelenRing =", value, "timelenring");
            return (Criteria) this;
        }

        public Criteria andTimelenringNotEqualTo(Integer value) {
            addCriterion("TimelenRing <>", value, "timelenring");
            return (Criteria) this;
        }

        public Criteria andTimelenringGreaterThan(Integer value) {
            addCriterion("TimelenRing >", value, "timelenring");
            return (Criteria) this;
        }

        public Criteria andTimelenringGreaterThanOrEqualTo(Integer value) {
            addCriterion("TimelenRing >=", value, "timelenring");
            return (Criteria) this;
        }

        public Criteria andTimelenringLessThan(Integer value) {
            addCriterion("TimelenRing <", value, "timelenring");
            return (Criteria) this;
        }

        public Criteria andTimelenringLessThanOrEqualTo(Integer value) {
            addCriterion("TimelenRing <=", value, "timelenring");
            return (Criteria) this;
        }

        public Criteria andTimelenringIn(List<Integer> values) {
            addCriterion("TimelenRing in", values, "timelenring");
            return (Criteria) this;
        }

        public Criteria andTimelenringNotIn(List<Integer> values) {
            addCriterion("TimelenRing not in", values, "timelenring");
            return (Criteria) this;
        }

        public Criteria andTimelenringBetween(Integer value1, Integer value2) {
            addCriterion("TimelenRing between", value1, value2, "timelenring");
            return (Criteria) this;
        }

        public Criteria andTimelenringNotBetween(Integer value1, Integer value2) {
            addCriterion("TimelenRing not between", value1, value2, "timelenring");
            return (Criteria) this;
        }

        public Criteria andTimelenspeakIsNull() {
            addCriterion("TimelenSpeak is null");
            return (Criteria) this;
        }

        public Criteria andTimelenspeakIsNotNull() {
            addCriterion("TimelenSpeak is not null");
            return (Criteria) this;
        }

        public Criteria andTimelenspeakEqualTo(Integer value) {
            addCriterion("TimelenSpeak =", value, "timelenspeak");
            return (Criteria) this;
        }

        public Criteria andTimelenspeakNotEqualTo(Integer value) {
            addCriterion("TimelenSpeak <>", value, "timelenspeak");
            return (Criteria) this;
        }

        public Criteria andTimelenspeakGreaterThan(Integer value) {
            addCriterion("TimelenSpeak >", value, "timelenspeak");
            return (Criteria) this;
        }

        public Criteria andTimelenspeakGreaterThanOrEqualTo(Integer value) {
            addCriterion("TimelenSpeak >=", value, "timelenspeak");
            return (Criteria) this;
        }

        public Criteria andTimelenspeakLessThan(Integer value) {
            addCriterion("TimelenSpeak <", value, "timelenspeak");
            return (Criteria) this;
        }

        public Criteria andTimelenspeakLessThanOrEqualTo(Integer value) {
            addCriterion("TimelenSpeak <=", value, "timelenspeak");
            return (Criteria) this;
        }

        public Criteria andTimelenspeakIn(List<Integer> values) {
            addCriterion("TimelenSpeak in", values, "timelenspeak");
            return (Criteria) this;
        }

        public Criteria andTimelenspeakNotIn(List<Integer> values) {
            addCriterion("TimelenSpeak not in", values, "timelenspeak");
            return (Criteria) this;
        }

        public Criteria andTimelenspeakBetween(Integer value1, Integer value2) {
            addCriterion("TimelenSpeak between", value1, value2, "timelenspeak");
            return (Criteria) this;
        }

        public Criteria andTimelenspeakNotBetween(Integer value1, Integer value2) {
            addCriterion("TimelenSpeak not between", value1, value2, "timelenspeak");
            return (Criteria) this;
        }

        public Criteria andScoresatisIsNull() {
            addCriterion("ScoreSatis is null");
            return (Criteria) this;
        }

        public Criteria andScoresatisIsNotNull() {
            addCriterion("ScoreSatis is not null");
            return (Criteria) this;
        }

        public Criteria andScoresatisEqualTo(Integer value) {
            addCriterion("ScoreSatis =", value, "scoresatis");
            return (Criteria) this;
        }

        public Criteria andScoresatisNotEqualTo(Integer value) {
            addCriterion("ScoreSatis <>", value, "scoresatis");
            return (Criteria) this;
        }

        public Criteria andScoresatisGreaterThan(Integer value) {
            addCriterion("ScoreSatis >", value, "scoresatis");
            return (Criteria) this;
        }

        public Criteria andScoresatisGreaterThanOrEqualTo(Integer value) {
            addCriterion("ScoreSatis >=", value, "scoresatis");
            return (Criteria) this;
        }

        public Criteria andScoresatisLessThan(Integer value) {
            addCriterion("ScoreSatis <", value, "scoresatis");
            return (Criteria) this;
        }

        public Criteria andScoresatisLessThanOrEqualTo(Integer value) {
            addCriterion("ScoreSatis <=", value, "scoresatis");
            return (Criteria) this;
        }

        public Criteria andScoresatisIn(List<Integer> values) {
            addCriterion("ScoreSatis in", values, "scoresatis");
            return (Criteria) this;
        }

        public Criteria andScoresatisNotIn(List<Integer> values) {
            addCriterion("ScoreSatis not in", values, "scoresatis");
            return (Criteria) this;
        }

        public Criteria andScoresatisBetween(Integer value1, Integer value2) {
            addCriterion("ScoreSatis between", value1, value2, "scoresatis");
            return (Criteria) this;
        }

        public Criteria andScoresatisNotBetween(Integer value1, Integer value2) {
            addCriterion("ScoreSatis not between", value1, value2, "scoresatis");
            return (Criteria) this;
        }

        public Criteria andScorequalityIsNull() {
            addCriterion("ScoreQuality is null");
            return (Criteria) this;
        }

        public Criteria andScorequalityIsNotNull() {
            addCriterion("ScoreQuality is not null");
            return (Criteria) this;
        }

        public Criteria andScorequalityEqualTo(Integer value) {
            addCriterion("ScoreQuality =", value, "scorequality");
            return (Criteria) this;
        }

        public Criteria andScorequalityNotEqualTo(Integer value) {
            addCriterion("ScoreQuality <>", value, "scorequality");
            return (Criteria) this;
        }

        public Criteria andScorequalityGreaterThan(Integer value) {
            addCriterion("ScoreQuality >", value, "scorequality");
            return (Criteria) this;
        }

        public Criteria andScorequalityGreaterThanOrEqualTo(Integer value) {
            addCriterion("ScoreQuality >=", value, "scorequality");
            return (Criteria) this;
        }

        public Criteria andScorequalityLessThan(Integer value) {
            addCriterion("ScoreQuality <", value, "scorequality");
            return (Criteria) this;
        }

        public Criteria andScorequalityLessThanOrEqualTo(Integer value) {
            addCriterion("ScoreQuality <=", value, "scorequality");
            return (Criteria) this;
        }

        public Criteria andScorequalityIn(List<Integer> values) {
            addCriterion("ScoreQuality in", values, "scorequality");
            return (Criteria) this;
        }

        public Criteria andScorequalityNotIn(List<Integer> values) {
            addCriterion("ScoreQuality not in", values, "scorequality");
            return (Criteria) this;
        }

        public Criteria andScorequalityBetween(Integer value1, Integer value2) {
            addCriterion("ScoreQuality between", value1, value2, "scorequality");
            return (Criteria) this;
        }

        public Criteria andScorequalityNotBetween(Integer value1, Integer value2) {
            addCriterion("ScoreQuality not between", value1, value2, "scorequality");
            return (Criteria) this;
        }

        public Criteria andDtmfIsNull() {
            addCriterion("DTMF is null");
            return (Criteria) this;
        }

        public Criteria andDtmfIsNotNull() {
            addCriterion("DTMF is not null");
            return (Criteria) this;
        }

        public Criteria andDtmfEqualTo(String value) {
            addCriterion("DTMF =", value, "dtmf");
            return (Criteria) this;
        }

        public Criteria andDtmfNotEqualTo(String value) {
            addCriterion("DTMF <>", value, "dtmf");
            return (Criteria) this;
        }

        public Criteria andDtmfGreaterThan(String value) {
            addCriterion("DTMF >", value, "dtmf");
            return (Criteria) this;
        }

        public Criteria andDtmfGreaterThanOrEqualTo(String value) {
            addCriterion("DTMF >=", value, "dtmf");
            return (Criteria) this;
        }

        public Criteria andDtmfLessThan(String value) {
            addCriterion("DTMF <", value, "dtmf");
            return (Criteria) this;
        }

        public Criteria andDtmfLessThanOrEqualTo(String value) {
            addCriterion("DTMF <=", value, "dtmf");
            return (Criteria) this;
        }

        public Criteria andDtmfLike(String value) {
            addCriterion("DTMF like", value, "dtmf");
            return (Criteria) this;
        }

        public Criteria andDtmfNotLike(String value) {
            addCriterion("DTMF not like", value, "dtmf");
            return (Criteria) this;
        }

        public Criteria andDtmfIn(List<String> values) {
            addCriterion("DTMF in", values, "dtmf");
            return (Criteria) this;
        }

        public Criteria andDtmfNotIn(List<String> values) {
            addCriterion("DTMF not in", values, "dtmf");
            return (Criteria) this;
        }

        public Criteria andDtmfBetween(String value1, String value2) {
            addCriterion("DTMF between", value1, value2, "dtmf");
            return (Criteria) this;
        }

        public Criteria andDtmfNotBetween(String value1, String value2) {
            addCriterion("DTMF not between", value1, value2, "dtmf");
            return (Criteria) this;
        }

        public Criteria andCalloidIsNull() {
            addCriterion("CallOID is null");
            return (Criteria) this;
        }

        public Criteria andCalloidIsNotNull() {
            addCriterion("CallOID is not null");
            return (Criteria) this;
        }

        public Criteria andCalloidEqualTo(String value) {
            addCriterion("CallOID =", value, "calloid");
            return (Criteria) this;
        }

        public Criteria andCalloidNotEqualTo(String value) {
            addCriterion("CallOID <>", value, "calloid");
            return (Criteria) this;
        }

        public Criteria andCalloidGreaterThan(String value) {
            addCriterion("CallOID >", value, "calloid");
            return (Criteria) this;
        }

        public Criteria andCalloidGreaterThanOrEqualTo(String value) {
            addCriterion("CallOID >=", value, "calloid");
            return (Criteria) this;
        }

        public Criteria andCalloidLessThan(String value) {
            addCriterion("CallOID <", value, "calloid");
            return (Criteria) this;
        }

        public Criteria andCalloidLessThanOrEqualTo(String value) {
            addCriterion("CallOID <=", value, "calloid");
            return (Criteria) this;
        }

        public Criteria andCalloidLike(String value) {
            addCriterion("CallOID like", value, "calloid");
            return (Criteria) this;
        }

        public Criteria andCalloidNotLike(String value) {
            addCriterion("CallOID not like", value, "calloid");
            return (Criteria) this;
        }

        public Criteria andCalloidIn(List<String> values) {
            addCriterion("CallOID in", values, "calloid");
            return (Criteria) this;
        }

        public Criteria andCalloidNotIn(List<String> values) {
            addCriterion("CallOID not in", values, "calloid");
            return (Criteria) this;
        }

        public Criteria andCalloidBetween(String value1, String value2) {
            addCriterion("CallOID between", value1, value2, "calloid");
            return (Criteria) this;
        }

        public Criteria andCalloidNotBetween(String value1, String value2) {
            addCriterion("CallOID not between", value1, value2, "calloid");
            return (Criteria) this;
        }

        public Criteria andQualitynumIsNull() {
            addCriterion("QualityNum is null");
            return (Criteria) this;
        }

        public Criteria andQualitynumIsNotNull() {
            addCriterion("QualityNum is not null");
            return (Criteria) this;
        }

        public Criteria andQualitynumEqualTo(String value) {
            addCriterion("QualityNum =", value, "qualitynum");
            return (Criteria) this;
        }

        public Criteria andQualitynumNotEqualTo(String value) {
            addCriterion("QualityNum <>", value, "qualitynum");
            return (Criteria) this;
        }

        public Criteria andQualitynumGreaterThan(String value) {
            addCriterion("QualityNum >", value, "qualitynum");
            return (Criteria) this;
        }

        public Criteria andQualitynumGreaterThanOrEqualTo(String value) {
            addCriterion("QualityNum >=", value, "qualitynum");
            return (Criteria) this;
        }

        public Criteria andQualitynumLessThan(String value) {
            addCriterion("QualityNum <", value, "qualitynum");
            return (Criteria) this;
        }

        public Criteria andQualitynumLessThanOrEqualTo(String value) {
            addCriterion("QualityNum <=", value, "qualitynum");
            return (Criteria) this;
        }

        public Criteria andQualitynumLike(String value) {
            addCriterion("QualityNum like", value, "qualitynum");
            return (Criteria) this;
        }

        public Criteria andQualitynumNotLike(String value) {
            addCriterion("QualityNum not like", value, "qualitynum");
            return (Criteria) this;
        }

        public Criteria andQualitynumIn(List<String> values) {
            addCriterion("QualityNum in", values, "qualitynum");
            return (Criteria) this;
        }

        public Criteria andQualitynumNotIn(List<String> values) {
            addCriterion("QualityNum not in", values, "qualitynum");
            return (Criteria) this;
        }

        public Criteria andQualitynumBetween(String value1, String value2) {
            addCriterion("QualityNum between", value1, value2, "qualitynum");
            return (Criteria) this;
        }

        public Criteria andQualitynumNotBetween(String value1, String value2) {
            addCriterion("QualityNum not between", value1, value2, "qualitynum");
            return (Criteria) this;
        }

        public Criteria andQualitytimeIsNull() {
            addCriterion("QualityTime is null");
            return (Criteria) this;
        }

        public Criteria andQualitytimeIsNotNull() {
            addCriterion("QualityTime is not null");
            return (Criteria) this;
        }

        public Criteria andQualitytimeEqualTo(String value) {
            addCriterion("QualityTime =", value, "qualitytime");
            return (Criteria) this;
        }

        public Criteria andQualitytimeNotEqualTo(String value) {
            addCriterion("QualityTime <>", value, "qualitytime");
            return (Criteria) this;
        }

        public Criteria andQualitytimeGreaterThan(String value) {
            addCriterion("QualityTime >", value, "qualitytime");
            return (Criteria) this;
        }

        public Criteria andQualitytimeGreaterThanOrEqualTo(String value) {
            addCriterion("QualityTime >=", value, "qualitytime");
            return (Criteria) this;
        }

        public Criteria andQualitytimeLessThan(String value) {
            addCriterion("QualityTime <", value, "qualitytime");
            return (Criteria) this;
        }

        public Criteria andQualitytimeLessThanOrEqualTo(String value) {
            addCriterion("QualityTime <=", value, "qualitytime");
            return (Criteria) this;
        }

        public Criteria andQualitytimeLike(String value) {
            addCriterion("QualityTime like", value, "qualitytime");
            return (Criteria) this;
        }

        public Criteria andQualitytimeNotLike(String value) {
            addCriterion("QualityTime not like", value, "qualitytime");
            return (Criteria) this;
        }

        public Criteria andQualitytimeIn(List<String> values) {
            addCriterion("QualityTime in", values, "qualitytime");
            return (Criteria) this;
        }

        public Criteria andQualitytimeNotIn(List<String> values) {
            addCriterion("QualityTime not in", values, "qualitytime");
            return (Criteria) this;
        }

        public Criteria andQualitytimeBetween(String value1, String value2) {
            addCriterion("QualityTime between", value1, value2, "qualitytime");
            return (Criteria) this;
        }

        public Criteria andQualitytimeNotBetween(String value1, String value2) {
            addCriterion("QualityTime not between", value1, value2, "qualitytime");
            return (Criteria) this;
        }

        public Criteria andQualityworknumIsNull() {
            addCriterion("QualityWorknum is null");
            return (Criteria) this;
        }

        public Criteria andQualityworknumIsNotNull() {
            addCriterion("QualityWorknum is not null");
            return (Criteria) this;
        }

        public Criteria andQualityworknumEqualTo(String value) {
            addCriterion("QualityWorknum =", value, "qualityworknum");
            return (Criteria) this;
        }

        public Criteria andQualityworknumNotEqualTo(String value) {
            addCriterion("QualityWorknum <>", value, "qualityworknum");
            return (Criteria) this;
        }

        public Criteria andQualityworknumGreaterThan(String value) {
            addCriterion("QualityWorknum >", value, "qualityworknum");
            return (Criteria) this;
        }

        public Criteria andQualityworknumGreaterThanOrEqualTo(String value) {
            addCriterion("QualityWorknum >=", value, "qualityworknum");
            return (Criteria) this;
        }

        public Criteria andQualityworknumLessThan(String value) {
            addCriterion("QualityWorknum <", value, "qualityworknum");
            return (Criteria) this;
        }

        public Criteria andQualityworknumLessThanOrEqualTo(String value) {
            addCriterion("QualityWorknum <=", value, "qualityworknum");
            return (Criteria) this;
        }

        public Criteria andQualityworknumLike(String value) {
            addCriterion("QualityWorknum like", value, "qualityworknum");
            return (Criteria) this;
        }

        public Criteria andQualityworknumNotLike(String value) {
            addCriterion("QualityWorknum not like", value, "qualityworknum");
            return (Criteria) this;
        }

        public Criteria andQualityworknumIn(List<String> values) {
            addCriterion("QualityWorknum in", values, "qualityworknum");
            return (Criteria) this;
        }

        public Criteria andQualityworknumNotIn(List<String> values) {
            addCriterion("QualityWorknum not in", values, "qualityworknum");
            return (Criteria) this;
        }

        public Criteria andQualityworknumBetween(String value1, String value2) {
            addCriterion("QualityWorknum between", value1, value2, "qualityworknum");
            return (Criteria) this;
        }

        public Criteria andQualityworknumNotBetween(String value1, String value2) {
            addCriterion("QualityWorknum not between", value1, value2, "qualityworknum");
            return (Criteria) this;
        }

        public Criteria andCompanycodeIsNull() {
            addCriterion("CompanyCode is null");
            return (Criteria) this;
        }

        public Criteria andCompanycodeIsNotNull() {
            addCriterion("CompanyCode is not null");
            return (Criteria) this;
        }

        public Criteria andCompanycodeEqualTo(String value) {
            addCriterion("CompanyCode =", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeNotEqualTo(String value) {
            addCriterion("CompanyCode <>", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeGreaterThan(String value) {
            addCriterion("CompanyCode >", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeGreaterThanOrEqualTo(String value) {
            addCriterion("CompanyCode >=", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeLessThan(String value) {
            addCriterion("CompanyCode <", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeLessThanOrEqualTo(String value) {
            addCriterion("CompanyCode <=", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeLike(String value) {
            addCriterion("CompanyCode like", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeNotLike(String value) {
            addCriterion("CompanyCode not like", value, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeIn(List<String> values) {
            addCriterion("CompanyCode in", values, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeNotIn(List<String> values) {
            addCriterion("CompanyCode not in", values, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeBetween(String value1, String value2) {
            addCriterion("CompanyCode between", value1, value2, "companycode");
            return (Criteria) this;
        }

        public Criteria andCompanycodeNotBetween(String value1, String value2) {
            addCriterion("CompanyCode not between", value1, value2, "companycode");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}