package com.honeyapple.chat.entity;

import java.time.ZonedDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor  
@AllArgsConstructor
@Table(name = "chat")
@Entity
public class ChatEntity {
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private int id;
	
	@Column(name = "postId")
	private int postId;
	
	@Column(name = "buyerId")
	private int buyerId;
	
	@Column(name = "tradeStatus")
	private String tradeStatus;   // 제안중 / 예약 / 완료
	
	@UpdateTimestamp
    @Column(name = "createdAt", updatable = false)
	private ZonedDateTime createdAt;
	
	@UpdateTimestamp
    @Column(name = "updatedAt")
	private ZonedDateTime updatedAt;
}
