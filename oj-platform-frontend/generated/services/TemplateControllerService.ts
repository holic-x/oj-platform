/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse_boolean_ } from "../models/BaseResponse_boolean_";
import type { BaseResponse_long_ } from "../models/BaseResponse_long_";
import type { BaseResponse_Page_Template_ } from "../models/BaseResponse_Page_Template_";
import type { BaseResponse_Page_TemplateVO_ } from "../models/BaseResponse_Page_TemplateVO_";
import type { BaseResponse_TemplateVO_ } from "../models/BaseResponse_TemplateVO_";
import type { BatchDeleteRequest } from "../models/BatchDeleteRequest";
import type { DeleteRequest } from "../models/DeleteRequest";
import type { TemplateAddRequest } from "../models/TemplateAddRequest";
import type { TemplateQueryRequest } from "../models/TemplateQueryRequest";
import type { TemplateStatusUpdateRequest } from "../models/TemplateStatusUpdateRequest";
import type { TemplateUpdateRequest } from "../models/TemplateUpdateRequest";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";
export class TemplateControllerService {
  /**
   * addTemplate
   * @param templateAddRequest templateAddRequest
   * @returns BaseResponse_long_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static addTemplateUsingPost(
    templateAddRequest: TemplateAddRequest
  ): CancelablePromise<BaseResponse_long_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/template/add",
      body: templateAddRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * batchDeleteTemplate
   * @param batchDeleteRequest batchDeleteRequest
   * @returns BaseResponse_boolean_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static batchDeleteTemplateUsingPost(
    batchDeleteRequest: BatchDeleteRequest
  ): CancelablePromise<BaseResponse_boolean_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/template/batchDeleteTemplate",
      body: batchDeleteRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * deleteTemplate
   * @param deleteRequest deleteRequest
   * @returns BaseResponse_boolean_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static deleteTemplateUsingPost(
    deleteRequest: DeleteRequest
  ): CancelablePromise<BaseResponse_boolean_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/template/delete",
      body: deleteRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * getTemplateVOById
   * @param id id
   * @returns BaseResponse_TemplateVO_ OK
   * @throws ApiError
   */
  public static getTemplateVoByIdUsingGet(
    id?: number
  ): CancelablePromise<BaseResponse_TemplateVO_> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/template/get/vo",
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
   * handleTemplateStatus
   * @param templateStatusUpdateRequest templateStatusUpdateRequest
   * @returns BaseResponse_boolean_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static handleTemplateStatusUsingPost(
    templateStatusUpdateRequest: TemplateStatusUpdateRequest
  ): CancelablePromise<BaseResponse_boolean_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/template/handleTemplateStatus",
      body: templateStatusUpdateRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * listTemplateByPage
   * @param templateQueryRequest templateQueryRequest
   * @returns BaseResponse_Page_Template_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static listTemplateByPageUsingPost(
    templateQueryRequest: TemplateQueryRequest
  ): CancelablePromise<BaseResponse_Page_Template_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/template/list/page",
      body: templateQueryRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * listTemplateVOByPage
   * @param templateQueryRequest templateQueryRequest
   * @returns BaseResponse_Page_TemplateVO_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static listTemplateVoByPageUsingPost(
    templateQueryRequest: TemplateQueryRequest
  ): CancelablePromise<BaseResponse_Page_TemplateVO_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/template/list/page/vo",
      body: templateQueryRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * updateTemplate
   * @param templateUpdateRequest templateUpdateRequest
   * @returns BaseResponse_boolean_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static updateTemplateUsingPost(
    templateUpdateRequest: TemplateUpdateRequest
  ): CancelablePromise<BaseResponse_boolean_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/template/update",
      body: templateUpdateRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
}
