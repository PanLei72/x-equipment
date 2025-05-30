package com.x.equipment.entity;

import com.x.equipment.constants.CheckListItemType;
import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@JmixEntity
@Table(name = "EQUI_EQUIPMENT_CHECK_JOB_ITEM", indexes = {
        @Index(name = "IDX_EQUI_EQUIPMENT_CHECK_JOB_ITEM_EQUIPMENT_CHECK_JOB", columnList = "EQUIPMENT_CHECK_JOB_ID")
})
@Entity(name = "EQUI_EquipmentCheckJobItem")
public class EquipmentCheckJobItem {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Long id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private OffsetDateTime createdDate;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private OffsetDateTime lastModifiedDate;

    @Column(name = "SEQUENCE")
    private Integer sequence;

    @Column(name = "JOB_ITEM_NAME", nullable = false)
    @NotNull
    private String jobItemName;

    @Column(name = "CATEGORY")
    private String category;

    @InstanceName
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CHECK_LIST_ITEM_TYPE", nullable = false)
    @NotNull
    private String checkListItemType;

    @Column(name = "CHECK_METHOD")
    private String checkMethod;

    @Column(name = "STANDARD_VALUE", precision = 19, scale = 2)
    private BigDecimal standardValue;

    @Column(name = "UPPER_LIMIT_VALUE", precision = 19, scale = 2)
    private BigDecimal upperLimitValue;

    @Column(name = "LOWER_LIMIT_VALUE", precision = 19, scale = 2)
    private BigDecimal lowerLimitValue;

    @Column(name = "CHECK_RESULT")
    private String checkResult;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "EQUIPMENT_CHECK_JOB_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private EquipmentCheckJob equipmentCheckJob;

    public String getCheckMethod() {
        return checkMethod;
    }

    public void setCheckMethod(String checkMethod) {
        this.checkMethod = checkMethod;
    }

    public EquipmentCheckJob getEquipmentCheckJob() {
        return equipmentCheckJob;
    }

    public void setEquipmentCheckJob(EquipmentCheckJob equipmentCheckJob) {
        this.equipmentCheckJob = equipmentCheckJob;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public BigDecimal getLowerLimitValue() {
        return lowerLimitValue;
    }

    public void setLowerLimitValue(BigDecimal maxValue) {
        this.lowerLimitValue = maxValue;
    }

    public BigDecimal getUpperLimitValue() {
        return upperLimitValue;
    }

    public void setUpperLimitValue(BigDecimal minValue) {
        this.upperLimitValue = minValue;
    }

    public BigDecimal getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(BigDecimal standardValue) {
        this.standardValue = standardValue;
    }

    public CheckListItemType getCheckListItemType() {
        return checkListItemType == null ? null : CheckListItemType.fromId(checkListItemType);
    }

    public void setCheckListItemType(CheckListItemType checkListItemType) {
        this.checkListItemType = checkListItemType == null ? null : checkListItemType.getId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getJobItemName() {
        return jobItemName;
    }

    public void setJobItemName(String jobItemName) {
        this.jobItemName = jobItemName;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public OffsetDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(OffsetDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}