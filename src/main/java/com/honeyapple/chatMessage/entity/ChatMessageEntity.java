package com.honeyapple.chatMessage.entity;

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
@Builder
@NoArgsConstructor  
@AllArgsConstructor
@Table(name = "chat_message")
@Entity
public class ChatMessageEntity {
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private int id;
	
	@Column(name = "chatId")
	private int chatId;
	
	@Column(name = "sellerId")
	private Integer sellerId;
	
	@Column(name = "buyerId")
	private Integer buyerId;
	
	private String content;
	
	@UpdateTimestamp
    @Column(name = "createdAt", updatable = false)
	private ZonedDateTime createdAt;
}
