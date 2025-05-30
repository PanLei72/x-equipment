package com.x.equipment.entity;

import com.x.equipment.constants.CheckCycleUnit;
import com.x.equipment.constants.JobStatus;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@JmixEntity
@Table(name = "EQUI_EQUIPMENT_CHECK_JOB", indexes = {
        @Index(name = "IDX_EQUI_EQUIPMENT_CHECK_JOB_CHECKLIST", columnList = "CHECKLIST_ID"),
        @Index(name = "IDX_EQUI_EQUIPMENT_CHECK_JOB_EQUIPMENT", columnList = "EQUIPMENT_ID")
})
@Entity(name = "EQUI_EquipmentCheckJob")
public class EquipmentCheckJob {
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

    @Column(name = "JOB_NAME", nullable = false, unique = true)
    @NotNull
    private String jobName;

    @InstanceName
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CATEGORY")
    private String category;

    @JoinColumn(name = "CHECKLIST_ID", nullable = false)
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private CheckList checklist;

    @JoinColumn(name = "EQUIPMENT_ID", nullable = false)
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Equipment equipment;

    @Column(name = "JOB_STATUS", nullable = false)
    @NotNull
    private String jobStatus;

    @Column(name = "PLAN_TIME", nullable = false)
    @NotNull
    private LocalDateTime planTime;

    @Column(name = "CHECK_CYCLE", nullable = false)
    @NotNull
    private Integer checkCycle;

    @Column(name = "CHECK_CYCLE_UNIT", nullable = false)
    @NotNull
    private String checkCycleUnit;

    @Column(name = "ACTUAL_TIME")
    private LocalDateTime actualTime;

    @Composition
    @OneToMany(mappedBy = "equipmentCheckJob")
    private List<EquipmentCheckJobItem> checkJobItems;

    public List<EquipmentCheckJobItem> getCheckJobItems() {
        return checkJobItems;
    }

    public void setCheckJobItems(List<EquipmentCheckJobItem> checkJobItems) {
        this.checkJobItems = checkJobItems;
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

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public LocalDateTime getActualTime() {
        return actualTime;
    }

    public void setActualTime(LocalDateTime actualTime) {
        this.actualTime = actualTime;
    }

    public CheckCycleUnit getCheckCycleUnit() {
        return checkCycleUnit == null ? null : CheckCycleUnit.fromId(checkCycleUnit);
    }

    public void setCheckCycleUnit(CheckCycleUnit checkCycleUnit) {
        this.checkCycleUnit = checkCycleUnit == null ? null : checkCycleUnit.getId();
    }

    public Integer getCheckCycle() {
        return checkCycle;
    }

    public void setCheckCycle(Integer checkCycle) {
        this.checkCycle = checkCycle;
    }

    public LocalDateTime getPlanTime() {
        return planTime;
    }

    public void setPlanTime(LocalDateTime planTime) {
        this.planTime = planTime;
    }

    public JobStatus getJobStatus() {
        return jobStatus == null ? null : JobStatus.fromId(jobStatus);
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus == null ? null : jobStatus.getId();
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public CheckList getChecklist() {
        return checklist;
    }

    public void setChecklist(CheckList checklist) {
        this.checklist = checklist;
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