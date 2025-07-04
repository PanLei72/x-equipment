package com.x.equipment.entity;

import com.x.equipment.constants.CheckCycleUnit;
import com.x.equipment.constants.CheckCategory;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;

@JmixEntity
@Table(name = "EQUI_CHECK_PLAN", uniqueConstraints = {
        @UniqueConstraint(name = "IDX_EQUI_CHECK_PLAN_UNQ", columnNames = {"CHECK_PLAN_NAME"})
})
@Entity(name = "EQUI_CheckPlan")
public class CheckPlan {
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

    @InstanceName
    @Column(name = "CHECK_PLAN_NAME", nullable = false)
    @NotNull
    private String checkPlanName;

    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "CATEGORY", nullable = false)
    private String category;

    @Column(name = "CHECK_CYCLE", nullable = false)
    @NotNull
    private Integer checkCycle;

    @Column(name = "CHECK_CYCLE_UNIT", nullable = false)
    @NotNull
    private String checkCycleUnit;

    @JoinColumn(name = "CHECKLIST_ID", nullable = false)
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private CheckList checklist;

    @JoinColumn(name = "EQUIPMENT_CLASS_ID", nullable = false)
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private EquipmentClass equipmentClass;

    public void setCategory(CheckCategory category) {
        this.category = category == null ? null : category.getId();
    }

    public CheckCategory getCategory() {
        return category == null ? null : CheckCategory.fromId(category);
    }

    public EquipmentClass getEquipmentClass() {
        return equipmentClass;
    }

    public void setEquipmentClass(EquipmentClass equipmentClass) {
        this.equipmentClass = equipmentClass;
    }

    public CheckList getChecklist() {
        return checklist;
    }

    public void setChecklist(CheckList checklist) {
        this.checklist = checklist;
    }

    public Integer getCheckCycle() {
        return checkCycle;
    }

    public void setCheckCycle(Integer checkCycle) {
        this.checkCycle = checkCycle;
    }

    public CheckCycleUnit getCheckCycleUnit() {
        return checkCycleUnit == null ? null : CheckCycleUnit.fromId(checkCycleUnit);
    }

    public void setCheckCycleUnit(CheckCycleUnit checkCycleUnit) {
        this.checkCycleUnit = checkCycleUnit == null ? null : checkCycleUnit.getId();
    }

    public String getCheckPlanName() {
        return checkPlanName;
    }

    public void setCheckPlanName(String checkPlanName) {
        this.checkPlanName = checkPlanName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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