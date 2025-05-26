package com.x.equipment.entity;

import com.x.equipment.constants.CheckListItemType;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
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
import java.util.UUID;

@JmixEntity
@Table(name = "EQUI_CHCEK_LIST_ITEM", indexes = {
        @Index(name = "IDX_EQUI_CHCEK_LIST_ITEM_CHECK_LIST", columnList = "CHECK_LIST_ID")
})
@Entity(name = "EQUI_ChcekListItem")
public class ChcekListItem {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

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

    @JoinColumn(name = "CHECK_LIST_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CheckList checkList;

    @Column(name = "SEQUENCE")
    private Integer sequence;

    @Column(name = "CHECKLIST_ITEM_NAME", nullable = false)
    @NotNull
    private String checklistItemName;

    @InstanceName
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "CHECK_METHOD")
    private String checkMethod;

    @Column(name = "CHECK_LIST_ITEM_TYPE", nullable = false)
    @NotNull
    private String checkListItemType;

    @Column(name = "STANDARD_VALUE", precision = 19, scale = 2)
    private BigDecimal standardValue;

    @Column(name = "LOWER_LIMIT_VALUE", precision = 19, scale = 2)
    private BigDecimal lowerLimitValue;

    @Column(name = "UPPER_LIMIT_VALUE", precision = 19, scale = 2)
    private BigDecimal upperLimitValue;

    public String getCheckMethod() {
        return checkMethod;
    }

    public void setCheckMethod(String checkMethod) {
        this.checkMethod = checkMethod;
    }

    public BigDecimal getUpperLimitValue() {
        return upperLimitValue;
    }

    public void setUpperLimitValue(BigDecimal maxValue) {
        this.upperLimitValue = maxValue;
    }

    public BigDecimal getLowerLimitValue() {
        return lowerLimitValue;
    }

    public void setLowerLimitValue(BigDecimal minValue) {
        this.lowerLimitValue = minValue;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChecklistItemName() {
        return checklistItemName;
    }

    public void setChecklistItemName(String checklistItemName) {
        this.checklistItemName = checklistItemName;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public CheckList getCheckList() {
        return checkList;
    }

    public void setCheckList(CheckList checkList) {
        this.checkList = checkList;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}