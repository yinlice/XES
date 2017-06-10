package com.wxzt.pojo;

import java.util.ArrayList;
import java.util.List;

public class WorkerDayReportExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WorkerDayReportExample() {
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

        public Criteria andWorknumIsNull() {
            addCriterion("worknum is null");
            return (Criteria) this;
        }

        public Criteria andWorknumIsNotNull() {
            addCriterion("worknum is not null");
            return (Criteria) this;
        }

        public Criteria andWorknumEqualTo(String value) {
            addCriterion("worknum =", value, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumNotEqualTo(String value) {
            addCriterion("worknum <>", value, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumGreaterThan(String value) {
            addCriterion("worknum >", value, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumGreaterThanOrEqualTo(String value) {
            addCriterion("worknum >=", value, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumLessThan(String value) {
            addCriterion("worknum <", value, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumLessThanOrEqualTo(String value) {
            addCriterion("worknum <=", value, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumLike(String value) {
            addCriterion("worknum like", value, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumNotLike(String value) {
            addCriterion("worknum not like", value, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumIn(List<String> values) {
            addCriterion("worknum in", values, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumNotIn(List<String> values) {
            addCriterion("worknum not in", values, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumBetween(String value1, String value2) {
            addCriterion("worknum between", value1, value2, "worknum");
            return (Criteria) this;
        }

        public Criteria andWorknumNotBetween(String value1, String value2) {
            addCriterion("worknum not between", value1, value2, "worknum");
            return (Criteria) this;
        }

        public Criteria andDtupdateIsNull() {
            addCriterion("dtUpdate is null");
            return (Criteria) this;
        }

        public Criteria andDtupdateIsNotNull() {
            addCriterion("dtUpdate is not null");
            return (Criteria) this;
        }

        public Criteria andDtupdateEqualTo(String value) {
            addCriterion("dtUpdate =", value, "dtupdate");
            return (Criteria) this;
        }

        public Criteria andDtupdateNotEqualTo(String value) {
            addCriterion("dtUpdate <>", value, "dtupdate");
            return (Criteria) this;
        }

        public Criteria andDtupdateGreaterThan(String value) {
            addCriterion("dtUpdate >", value, "dtupdate");
            return (Criteria) this;
        }

        public Criteria andDtupdateGreaterThanOrEqualTo(String value) {
            addCriterion("dtUpdate >=", value, "dtupdate");
            return (Criteria) this;
        }

        public Criteria andDtupdateLessThan(String value) {
            addCriterion("dtUpdate <", value, "dtupdate");
            return (Criteria) this;
        }

        public Criteria andDtupdateLessThanOrEqualTo(String value) {
            addCriterion("dtUpdate <=", value, "dtupdate");
            return (Criteria) this;
        }

        public Criteria andDtupdateLike(String value) {
            addCriterion("dtUpdate like", value, "dtupdate");
            return (Criteria) this;
        }

        public Criteria andDtupdateNotLike(String value) {
            addCriterion("dtUpdate not like", value, "dtupdate");
            return (Criteria) this;
        }

        public Criteria andDtupdateIn(List<String> values) {
            addCriterion("dtUpdate in", values, "dtupdate");
            return (Criteria) this;
        }

        public Criteria andDtupdateNotIn(List<String> values) {
            addCriterion("dtUpdate not in", values, "dtupdate");
            return (Criteria) this;
        }

        public Criteria andDtupdateBetween(String value1, String value2) {
            addCriterion("dtUpdate between", value1, value2, "dtupdate");
            return (Criteria) this;
        }

        public Criteria andDtupdateNotBetween(String value1, String value2) {
            addCriterion("dtUpdate not between", value1, value2, "dtupdate");
            return (Criteria) this;
        }

        public Criteria andCallIsNull() {
            addCriterion("call is null");
            return (Criteria) this;
        }

        public Criteria andCallIsNotNull() {
            addCriterion("call is not null");
            return (Criteria) this;
        }

        public Criteria andCallEqualTo(Integer value) {
            addCriterion("call =", value, "call");
            return (Criteria) this;
        }

        public Criteria andCallNotEqualTo(Integer value) {
            addCriterion("call <>", value, "call");
            return (Criteria) this;
        }

        public Criteria andCallGreaterThan(Integer value) {
            addCriterion("call >", value, "call");
            return (Criteria) this;
        }

        public Criteria andCallGreaterThanOrEqualTo(Integer value) {
            addCriterion("call >=", value, "call");
            return (Criteria) this;
        }

        public Criteria andCallLessThan(Integer value) {
            addCriterion("call <", value, "call");
            return (Criteria) this;
        }

        public Criteria andCallLessThanOrEqualTo(Integer value) {
            addCriterion("call <=", value, "call");
            return (Criteria) this;
        }

        public Criteria andCallIn(List<Integer> values) {
            addCriterion("call in", values, "call");
            return (Criteria) this;
        }

        public Criteria andCallNotIn(List<Integer> values) {
            addCriterion("call not in", values, "call");
            return (Criteria) this;
        }

        public Criteria andCallBetween(Integer value1, Integer value2) {
            addCriterion("call between", value1, value2, "call");
            return (Criteria) this;
        }

        public Criteria andCallNotBetween(Integer value1, Integer value2) {
            addCriterion("call not between", value1, value2, "call");
            return (Criteria) this;
        }

        public Criteria andNocallIsNull() {
            addCriterion("nocall is null");
            return (Criteria) this;
        }

        public Criteria andNocallIsNotNull() {
            addCriterion("nocall is not null");
            return (Criteria) this;
        }

        public Criteria andNocallEqualTo(Integer value) {
            addCriterion("nocall =", value, "nocall");
            return (Criteria) this;
        }

        public Criteria andNocallNotEqualTo(Integer value) {
            addCriterion("nocall <>", value, "nocall");
            return (Criteria) this;
        }

        public Criteria andNocallGreaterThan(Integer value) {
            addCriterion("nocall >", value, "nocall");
            return (Criteria) this;
        }

        public Criteria andNocallGreaterThanOrEqualTo(Integer value) {
            addCriterion("nocall >=", value, "nocall");
            return (Criteria) this;
        }

        public Criteria andNocallLessThan(Integer value) {
            addCriterion("nocall <", value, "nocall");
            return (Criteria) this;
        }

        public Criteria andNocallLessThanOrEqualTo(Integer value) {
            addCriterion("nocall <=", value, "nocall");
            return (Criteria) this;
        }

        public Criteria andNocallIn(List<Integer> values) {
            addCriterion("nocall in", values, "nocall");
            return (Criteria) this;
        }

        public Criteria andNocallNotIn(List<Integer> values) {
            addCriterion("nocall not in", values, "nocall");
            return (Criteria) this;
        }

        public Criteria andNocallBetween(Integer value1, Integer value2) {
            addCriterion("nocall between", value1, value2, "nocall");
            return (Criteria) this;
        }

        public Criteria andNocallNotBetween(Integer value1, Integer value2) {
            addCriterion("nocall not between", value1, value2, "nocall");
            return (Criteria) this;
        }

        public Criteria andTotalIsNull() {
            addCriterion("total is null");
            return (Criteria) this;
        }

        public Criteria andTotalIsNotNull() {
            addCriterion("total is not null");
            return (Criteria) this;
        }

        public Criteria andTotalEqualTo(Integer value) {
            addCriterion("total =", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotEqualTo(Integer value) {
            addCriterion("total <>", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThan(Integer value) {
            addCriterion("total >", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("total >=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThan(Integer value) {
            addCriterion("total <", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThanOrEqualTo(Integer value) {
            addCriterion("total <=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalIn(List<Integer> values) {
            addCriterion("total in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotIn(List<Integer> values) {
            addCriterion("total not in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalBetween(Integer value1, Integer value2) {
            addCriterion("total between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("total not between", value1, value2, "total");
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