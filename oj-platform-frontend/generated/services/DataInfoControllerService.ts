/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse_boolean_ } from "../models/BaseResponse_boolean_";
import type { BaseResponse_DataInfoVO_ } from "../models/BaseResponse_DataInfoVO_";
import type { BaseResponse_long_ } from "../models/BaseResponse_long_";
import type { BaseResponse_Page_DataInfo_ } from "../models/BaseResponse_Page_DataInfo_";
import type { BaseResponse_Page_DataInfoVO_ } from "../models/BaseResponse_Page_DataInfoVO_";
import type { BatchDeleteRequest } from "../models/BatchDeleteRequest";
import type { DataInfoAddRequest } from "../models/DataInfoAddRequest";
import type { DataInfoQueryRequest } from "../models/DataInfoQueryRequest";
import type { DataInfoStatusUpdateRequest } from "../models/DataInfoStatusUpdateRequest";
import type { DataInfoUpdateRequest } from "../models/DataInfoUpdateRequest";
import type { DeleteRequest } from "../models/DeleteRequest";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";
export class DataInfoControllerService {
  /**
   * addDataInfo
   * @param dataInfoAddRequest dataInfoAddRequest
   * @returns BaseResponse_long_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static addDataInfoUsingPost(
    dataInfoAddRequest: DataInfoAddRequest
  ): CancelablePromise<BaseResponse_long_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/dataInfo/add",
      body: dataInfoAddRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * batchDeleteDataInfo
   * @param batchDeleteRequest batchDeleteRequest
   * @returns BaseResponse_boolean_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static batchDeleteDataInfoUsingPost(
    batchDeleteRequest: BatchDeleteRequest
  ): CancelablePromise<BaseResponse_boolean_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/dataInfo/batchDeleteDataInfo",
      body: batchDeleteRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * deleteDataInfo
   * @param deleteRequest deleteRequest
   * @returns BaseResponse_boolean_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static deleteDataInfoUsingPost(
    deleteRequest: DeleteRequest
  ): CancelablePromise<BaseResponse_boolean_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/dataInfo/delete",
      body: deleteRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * getDataInfoVOById
   * @param id id
   * @returns BaseResponse_DataInfoVO_ OK
   * @throws ApiError
   */
  public static getDataInfoVoByIdUsingGet(
    id?: number
  ): CancelablePromise<BaseResponse_DataInfoVO_> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/dataInfo/get/vo",
      query: {
        id: id,
      },
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * handleDataInfoStatus
   * @param dataInfoStatusUpdateRequest dataInfoStatusUpdateRequest
   * @returns BaseResponse_boolean_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static handleDataInfoStatusUsingPost(
    dataInfoStatusUpdateRequest: DataInfoStatusUpdateRequest
  ): CancelablePromise<BaseResponse_boolean_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/dataInfo/handleDataInfoStatus",
      body: dataInfoStatusUpdateRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * listDataInfoByPage
   * @param dataInfoQueryRequest dataInfoQueryRequest
   * @returns BaseResponse_Page_DataInfo_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static listDataInfoByPageUsingPost(
    dataInfoQueryRequest: DataInfoQueryRequest
  ): CancelablePromise<BaseResponse_Page_DataInfo_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/dataInfo/list/page",
      body: dataInfoQueryRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * listDataInfoVOByPage
   * @param dataInfoQueryRequest dataInfoQueryRequest
   * @returns BaseResponse_Page_DataInfoVO_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static listDataInfoVoByPageUsingPost(
    dataInfoQueryRequest: DataInfoQueryRequest
  ): CancelablePromise<BaseResponse_Page_DataInfoVO_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/dataInfo/list/page/vo",
      body: dataInfoQueryRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * listByPage
   * @param dataInfoQueryRequest dataInfoQueryRequest
   * @returns BaseResponse_Page_DataInfoVO_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static listByPageUsingPost(
    dataInfoQueryRequest: DataInfoQueryRequest
  ): CancelablePromise<BaseResponse_Page_DataInfoVO_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/dataInfo/listByPage",
      body: dataInfoQueryRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * updateDataInfo
   * @param dataInfoUpdateRequest dataInfoUpdateRequest
   * @returns BaseResponse_boolean_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static updateDataInfoUsingPost(
    dataInfoUpdateRequest: DataInfoUpdateRequest
  ): CancelablePromise<BaseResponse_boolean_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/dataInfo/update",
      body: dataInfoUpdateRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
}
