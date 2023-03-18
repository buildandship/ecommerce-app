/**
 *  eCommerce Application
 *
 *  Copyright (c) 2022 - 2023 Ionut Balosin
 *  Website: www.ionutbalosin.com
 *  Twitter: @ionutbalosin / Mastodon: ionutbalosin@mastodon.socia
 *
 *
 *  MIT License
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */
package ionutbalosin.training.ecommerce.order.dto.mapper;

import static ionutbalosin.training.ecommerce.order.api.model.OrderDetailsDto.CurrencyEnum.fromValue;

import ionutbalosin.training.ecommerce.order.api.model.OrderDetailsDto;
import ionutbalosin.training.ecommerce.order.api.model.OrderDetailsDto.StatusEnum;
import ionutbalosin.training.ecommerce.order.model.Order;
import ionutbalosin.training.ecommerce.order.model.OrderStatus;

public class OrderDetailsDtoMapper {

  public OrderDetailsDto map(Order order) {
    return new OrderDetailsDto()
        .orderId(order.getId())
        .userId(order.getUserId())
        .amount(order.getAmount())
        .currency(fromValue(order.getCurrency()))
        .details(order.getDetails())
        .status(OrderToOrderDetailsDtoStatusMapper.map(order.getStatus()));
  }

  private enum OrderToOrderDetailsDtoStatusMapper {
    PAYMENT_INITIATED(StatusEnum.PAYMENT_INITIATED, OrderStatus.PAYMENT_INITIATED),
    PAYMENT_APPROVED(StatusEnum.PAYMENT_APPROVED, OrderStatus.PAYMENT_APPROVED),
    PAYMENT_FAILED(StatusEnum.PAYMENT_FAILED, OrderStatus.PAYMENT_FAILED),
    SHIPPING(StatusEnum.SHIPPING, OrderStatus.SHIPPING),
    COMPLETED(StatusEnum.COMPLETED, OrderStatus.COMPLETED),
    CANCELLED(StatusEnum.CANCELLED, OrderStatus.CANCELLED);

    private StatusEnum dtoStatus;
    private OrderStatus modelStatus;

    OrderToOrderDetailsDtoStatusMapper(StatusEnum dtoStatus, OrderStatus modelStatus) {
      this.dtoStatus = dtoStatus;
      this.modelStatus = modelStatus;
    }

    private static StatusEnum map(OrderStatus modelStatus) {
      for (OrderToOrderDetailsDtoStatusMapper orderStatus :
          OrderToOrderDetailsDtoStatusMapper.values()) {
        if (orderStatus.modelStatus == modelStatus) {
          return orderStatus.dtoStatus;
        }
      }
      throw new IllegalArgumentException("Unexpected status '" + modelStatus + "'");
    }
  }
}
