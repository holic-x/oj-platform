/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DataInfoVO } from "./DataInfoVO";
import type { OrderItem } from "./OrderItem";
export type Page_DataInfoVO_ = {
  countId?: string;
  current?: number;
  maxLimit?: number;
  optimizeCountSql?: boolean;
  orders?: Array<OrderItem>;
  pages?: number;
  records?: Array<DataInfoVO>;
  searchCount?: boolean;
  size?: number;
  total?: number;
};
