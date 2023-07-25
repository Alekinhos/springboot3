import axios, { AxiosPromise } from "axios"
import { ProdutoData } from "../interface/ProdutoDate";
import { useMutation, useQueryClient } from "@tanstack/react-query";

const API_URL = "http://localhost:8080";

const postData = async (data: ProdutoData): AxiosPromise<any> => {
    const response = axios.post(API_URL + '/products', data);
    return response;
}

export function useProdutoDataMutation() {
    const queryClient = useQueryClient();
    const mutate = useMutation({
        mutationFn: postData,
        retry: 2,
        onSuccess: () => {
            queryClient.invalidateQueries(['produtos'])
        }

    })

   
 return mutate;
}