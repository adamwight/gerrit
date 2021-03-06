// Copyright (C) 2011 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.gerrit.httpd.rpc.changedetail;

import com.google.gerrit.httpd.rpc.Handler;
import com.google.gerrit.reviewdb.client.Change;
import com.google.gerrit.reviewdb.client.PatchSet;
import com.google.gerrit.reviewdb.server.ReviewDb;
import com.google.gerrit.server.ChangeUtil;
import com.google.gerrit.server.extensions.events.GitReferenceUpdated;
import com.google.gerrit.server.git.GitRepositoryManager;
import com.google.gerrit.server.project.ChangeControl;
import com.google.gerrit.server.project.NoSuchChangeException;
import com.google.gwtjsonrpc.common.VoidResult;
import com.google.gwtorm.server.OrmException;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import java.io.IOException;

class DeleteDraftChange extends Handler<VoidResult> {
  interface Factory {
    DeleteDraftChange create(PatchSet.Id patchSetId);
  }

  private final ChangeControl.Factory changeControlFactory;
  private final ReviewDb db;
  private final GitRepositoryManager gitManager;
  private final GitReferenceUpdated gitRefUpdated;

  private final PatchSet.Id patchSetId;

  @Inject
  DeleteDraftChange(final ReviewDb db,
      final ChangeControl.Factory changeControlFactory,
      final GitRepositoryManager gitManager,
      final GitReferenceUpdated gitRefUpdated,
      @Assisted final PatchSet.Id patchSetId) {
    this.changeControlFactory = changeControlFactory;
    this.db = db;
    this.gitManager = gitManager;
    this.gitRefUpdated = gitRefUpdated;

    this.patchSetId = patchSetId;
  }

  @Override
  public VoidResult call() throws NoSuchChangeException, OrmException, IOException {

    final Change.Id changeId = patchSetId.getParentKey();
    final ChangeControl control = changeControlFactory.validateFor(changeId);
    if (!control.canDeleteDraft(db)) {
      throw new NoSuchChangeException(changeId);
    }

    ChangeUtil.deleteDraftChange(patchSetId, gitManager, gitRefUpdated, db);
    return VoidResult.INSTANCE;
  }
}
