package br.com.coderbank.movimentacoes.entities;

import br.com.coderbank.movimentacoes.entities.enums.TransactionStatus;
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
    private BigDecimal amount;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column
    private String idAccountSource;

    @Column
    private String idAccountDestination;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column
    @CreationTimestamp
    private String createdAt;

    public UUID getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(UUID idTransaction) {
        this.idTransaction = idTransaction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public String getIdAccountSource() {
        return idAccountSource;
    }

    public void setIdAccountSource(String idAccountSource) {
        this.idAccountSource = idAccountSource;
    }

    public String getIdAccountDestination() {
        return idAccountDestination;
    }

    public void setIdAccountDestination(String idAccountDestination) {
        this.idAccountDestination = idAccountDestination;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
