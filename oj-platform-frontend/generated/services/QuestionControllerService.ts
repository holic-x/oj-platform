/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse_boolean_ } from "../models/BaseResponse_boolean_";
import type { BaseResponse_long_ } from "../models/BaseResponse_long_";
import type { BaseResponse_Page_QuestionVO_ } from "../models/BaseResponse_Page_QuestionVO_";
import type { BaseResponse_QuestionVO_ } from "../models/BaseResponse_QuestionVO_";
import type { BatchDeleteRequest } from "../models/BatchDeleteRequest";
import type { DeleteRequest } from "../models/DeleteRequest";
import type { QuestionAddRequest } from "../models/QuestionAddRequest";
import type { QuestionQueryRequest } from "../models/QuestionQueryRequest";
import type { QuestionUpdateRequest } from "../models/QuestionUpdateRequest";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";
export class QuestionControllerService {
  /**
   * addQuestion
   * @param questionAddRequest questionAddRequest
   * @returns BaseResponse_long_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static addQuestionUsingPost(
    questionAddRequest: QuestionAddRequest
  ): CancelablePromise<BaseResponse_long_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/question/add",
      body: questionAddRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * batchDeleteQuestion
   * @param batchDeleteRequest batchDeleteRequest
   * @returns BaseResponse_boolean_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static batchDeleteQuestionUsingPost(
    batchDeleteRequest: BatchDeleteRequest
  ): CancelablePromise<BaseResponse_boolean_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/question/batchDeleteQuestion",
      body: batchDeleteRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * deleteQuestion
   * @param deleteRequest deleteRequest
   * @returns BaseResponse_boolean_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static deleteQuestionUsingPost(
    deleteRequest: DeleteRequest
  ): CancelablePromise<BaseResponse_boolean_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/question/delete",
      body: deleteRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * getQuestionVOById
   * @param id id
   * @returns BaseResponse_QuestionVO_ OK
   * @throws ApiError
   */
  public static getQuestionVoByIdUsingGet(
    id?: number
  ): CancelablePromise<BaseResponse_QuestionVO_> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/question/get/vo",
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
   * @param questionQueryRequest questionQueryRequest
   * @returns BaseResponse_Page_QuestionVO_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static getVoByPageUsingPost(
    questionQueryRequest: QuestionQueryRequest
  ): CancelablePromise<BaseResponse_Page_QuestionVO_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/question/getVOByPage",
      body: questionQueryRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
  /**
   * updateQuestion
   * @param questionUpdateRequest questionUpdateRequest
   * @returns BaseResponse_boolean_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static updateQuestionUsingPost(
    questionUpdateRequest: QuestionUpdateRequest
  ): CancelablePromise<BaseResponse_boolean_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/question/update",
      body: questionUpdateRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
}
