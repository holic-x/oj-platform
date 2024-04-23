/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse_boolean_ } from "../models/BaseResponse_boolean_";
import type { BaseResponse_long_ } from "../models/BaseResponse_long_";
import type { BaseResponse_Page_QuestionSubmitVO_ } from "../models/BaseResponse_Page_QuestionSubmitVO_";
import type { BaseResponse_QuestionSubmitVO_ } from "../models/BaseResponse_QuestionSubmitVO_";
import type { BatchDeleteRequest } from "../models/BatchDeleteRequest";
import type { DeleteRequest } from "../models/DeleteRequest";
import type { QuestionSubmitAddRequest } from "../models/QuestionSubmitAddRequest";
import type { QuestionSubmitQueryRequest } from "../models/QuestionSubmitQueryRequest";
import type { QuestionSubmitUpdateRequest } from "../models/QuestionSubmitUpdateRequest";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";
export class QuestionSubmitControllerService {
  /**
   * addQuestionSubmit
   * @param questionSubmitAddRequest questionSubmitAddRequest
   * @returns BaseResponse_long_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static addQuestionSubmitUsingPost(
    questionSubmitAddRequest: QuestionSubmitAddRequest
  ): CancelablePromise<BaseResponse_long_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/questionSubmit/add",
      body: questionSubmitAddRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * batchDeleteQuestionSubmit
   * @param batchDeleteRequest batchDeleteRequest
   * @returns BaseResponse_boolean_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static batchDeleteQuestionSubmitUsingPost(
    batchDeleteRequest: BatchDeleteRequest
  ): CancelablePromise<BaseResponse_boolean_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/questionSubmit/batchDeleteQuestionSubmit",
      body: batchDeleteRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * deleteQuestionSubmit
   * @param deleteRequest deleteRequest
   * @returns BaseResponse_boolean_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static deleteQuestionSubmitUsingPost(
    deleteRequest: DeleteRequest
  ): CancelablePromise<BaseResponse_boolean_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/questionSubmit/delete",
      body: deleteRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * getQuestionSubmitVOById
   * @param id id
   * @returns BaseResponse_QuestionSubmitVO_ OK
   * @throws ApiError
   */
  public static getQuestionSubmitVoByIdUsingGet(
    id?: number
  ): CancelablePromise<BaseResponse_QuestionSubmitVO_> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/questionSubmit/get/vo",
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
   * getVOByPage
   * @param questionSubmitQueryRequest questionSubmitQueryRequest
   * @returns BaseResponse_Page_QuestionSubmitVO_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static getVoByPageUsingPost1(
    questionSubmitQueryRequest: QuestionSubmitQueryRequest
  ): CancelablePromise<BaseResponse_Page_QuestionSubmitVO_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/questionSubmit/getVOByPage",
      body: questionSubmitQueryRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * updateQuestionSubmit
   * @param questionSubmitUpdateRequest questionSubmitUpdateRequest
   * @returns BaseResponse_boolean_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static updateQuestionSubmitUsingPost(
    questionSubmitUpdateRequest: QuestionSubmitUpdateRequest
  ): CancelablePromise<BaseResponse_boolean_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/questionSubmit/update",
      body: questionSubmitUpdateRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
}
