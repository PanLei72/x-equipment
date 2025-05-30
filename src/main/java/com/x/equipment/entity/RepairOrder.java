package com.x.equipment.entity;

import com.x.equipment.constants.RepairOrderStatus;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
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

@JmixEntity
@Table(name = "EQUI_REPAIR_ORDER", uniqueConstraints = {
        @UniqueConstraint(name = "IDX_EQUI_REPAIR_ORDER_UNQ", columnNames = {"ORDER_NUMBER"})
})
@Entity(name = "EQUI_RepairOrder")
public class RepairOrder {
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

    @Column(name = "ORDER_NUMBER", nullable = false)
    @NotNull
    private String orderNumber;

    @InstanceName
    @Column(name = "DESCRIPTION")
    private String description;

    @JoinColumn(name = "EQUIPMENT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Equipment equipment;

    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    @Column(name = "REPAIR_TIME")
    private LocalDateTime repairTime;

    @Column(name = "START_REPAIR_TIME")
    private LocalDateTime startRepairTime;

    @Column(name = "COMPLETE_REPAIR_TIME")
    private LocalDateTime completeRepairTime;

    @JoinColumn(name = "FAULT_LEVEL_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FaultLevel faultLevel;

    @JoinColumn(name = "FAULT_TYPE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FaultType faultType;


    public FaultLevel getFaultLevel() {
        return faultLevel;
    }

    public void setFaultLevel(FaultLevel faultLevel) {
        this.faultLevel = faultLevel;
    }

    public FaultType getFaultType() {
        return faultType;
    }

    public void setFaultType(FaultType faultType) {
        this.faultType = faultType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCompleteRepairTime() {
        return completeRepairTime;
    }

    public void setCompleteRepairTime(LocalDateTime completeRepairTime) {
        this.completeRepairTime = completeRepairTime;
    }

    public LocalDateTime getStartRepairTime() {
        return startRepairTime;
    }

    public void setStartRepairTime(LocalDateTime startRepairTime) {
        this.startRepairTime = startRepairTime;
    }

    public void setRepairTime(LocalDateTime repairTime) {
        this.repairTime = repairTime;
    }

    public LocalDateTime getRepairTime() {
        return repairTime;
    }

    public RepairOrderStatus getOrderStatus() {
        return orderStatus == null ? null : RepairOrderStatus.fromId(orderStatus);
    }

    public void setOrderStatus(RepairOrderStatus orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.getId();
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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