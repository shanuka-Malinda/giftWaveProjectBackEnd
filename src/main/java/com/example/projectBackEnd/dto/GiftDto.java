package com.example.projectBackEnd.dto;
import com.example.projectBackEnd.constant.CommonStatus;
import com.example.projectBackEnd.entity.Items;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GiftDto {
    private Long id;
    private String giftName;
    private String createdAt;
    private String SendingDate;
    private CommonStatus commonStatus;
    private Set<Long> itemIds;
   // private Set<ItemsDto> items;
}
