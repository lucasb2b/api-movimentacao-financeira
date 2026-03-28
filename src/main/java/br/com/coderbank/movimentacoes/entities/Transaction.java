package br.com.coderbank.movimentacoes.entities;

import br.com.coderbank.movimentacoes.entities.enums.TransactionType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "CB_TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idTransaction;

    @Column
    private BigDecimal value;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column
    private Account idAccountSource;

    @Column
    private Account idAccountDestination;

    @Column
    @CreationTimestamp
    private String createdAt;

    public UUID getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(UUID idTransaction) {
        this.idTransaction = idTransaction;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Account getIdAccountSource() {
        return idAccountSource;
    }

    public void setIdAccountSource(Account idAccountSource) {
        this.idAccountSource = idAccountSource;
    }

    public Account getIdAccountDestination() {
        return idAccountDestination;
    }

    public void setIdAccountDestination(Account idAccountDestination) {
        this.idAccountDestination = idAccountDestination;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
