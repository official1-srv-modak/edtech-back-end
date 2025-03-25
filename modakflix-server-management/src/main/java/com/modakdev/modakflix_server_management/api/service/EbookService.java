package com.modakdev.modakflix_server_management.api.service;

import com.modakdev.models.nonentities.response.management.ModakFlixEbookCategoricalResponse;

public interface EbookService {

    public ModakFlixEbookCategoricalResponse getDirectoryStructure(String ebookPath);
}
